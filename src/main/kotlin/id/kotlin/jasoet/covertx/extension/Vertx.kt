package id.kotlin.jasoet.covertx.extension

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.AsyncResult
import io.vertx.core.DeploymentOptions
import io.vertx.core.Handler
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.kotlin.config.ConfigRetrieverOptions
import io.vertx.kotlin.config.ConfigStoreOptions
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo
 */

inline suspend fun <T> cov(crossinline callback: (Handler<AsyncResult<T>>) -> Unit): T {
    return suspendCoroutine { continuation ->
        callback(Handler { result: AsyncResult<T> ->
            if (result.succeeded()) {
                continuation.resume(result.result())
            } else {
                continuation.resumeWithException(result.cause())
            }
        })
    }
}

suspend fun Vertx.createHttpServer(router: Router, port: Int): HttpServer {
    val httpServer = this.createHttpServer()
        .requestHandler { request -> router.accept(request) }
    return cov { httpServer.listen(port, it) }
}

suspend fun Vertx.deployVerticle(verticle: Verticle, config: JsonObject, worker: Boolean = false): String {
    val option = DeploymentOptions().apply {
        this.config = config
        this.isWorker = worker
    }

    return cov { this.deployVerticle(verticle, option, it) }
}

suspend fun Vertx.retrieveConfig(vararg stores: ConfigStoreOptions): JsonObject {
    val envConfig = ConfigStoreOptions(type = "env")

    val options = ConfigRetrieverOptions(
        stores = stores.toList().plus(envConfig)
    )

    val retriever = ConfigRetriever.create(this, options)
    return cov { retriever.getConfig(it) }
}
