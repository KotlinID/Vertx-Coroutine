package id.kotlin.jasoet.covertx.controller

import id.kotlin.jasoet.covertx.extension.logger
import io.vertx.ext.web.Router

/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo.
 */


class MainController constructor(override val router: Router) : Controller({

    val log = logger(MainController::class)

    get("/").handler { context ->
        val request = context.request()
        val name = request.getParam("name") ?: "Tidak ada Nama"

        val response = context.response()
        response.statusCode = 200
        response.end("Hello $name")
    }

})