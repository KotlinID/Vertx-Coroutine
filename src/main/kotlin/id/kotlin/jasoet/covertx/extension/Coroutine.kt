package id.kotlin.jasoet.covertx.extension

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
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
