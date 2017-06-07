package id.kotlin.jasoet.covertx.verticle

import id.kotlin.jasoet.covertx.controller.MainController
import id.kotlin.jasoet.covertx.extension.createHttpServer
import id.kotlin.jasoet.covertx.extension.logger
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo.
 */

class MainVerticle constructor(val mainController: MainController) : AbstractVerticle() {
    private val log = logger(MainVerticle::class)
    private val config by lazy { config() }

    override fun start(startFuture: Future<Void>) {
        launch(CommonPool) {
            log.info("Initialize Main Verticle...")
            log.info("Initialize Router...")

            val router = mainController.create()
            val port = config.getInteger("HTTP_PORT")
            try {
                log.info("Starting HttpServer on port $port")
                val httpServer = vertx.createHttpServer(router, port)
                log.info("HttpServer started in port ${httpServer.actualPort()}")
                log.info("Main Verticle Deployed!")
                startFuture.complete()
            } catch (e: Exception) {
                startFuture.fail(e)
            }
        }
    }
}