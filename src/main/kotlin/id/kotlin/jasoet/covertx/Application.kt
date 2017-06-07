package id.kotlin.jasoet.covertx

import id.kotlin.jasoet.covertx.controller.MainController
import id.kotlin.jasoet.covertx.extension.logger
import id.kotlin.jasoet.covertx.extension.propertiesConfig
import id.kotlin.jasoet.covertx.extension.retrieveConfig
import id.kotlin.jasoet.covertx.extension.useLogback
import id.kotlin.jasoet.covertx.verticle.MainVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import kotlinx.coroutines.experimental.runBlocking


/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo
 */

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runBlocking {
                useLogback()
                val log = logger(Application::class)

                log.info("Initialize Vertx Components")

                val vertx = Vertx.vertx()
                val config = vertx.retrieveConfig(propertiesConfig("application-config.properties"))

                val router = Router.router(vertx)
                log.info("Deploying Main Verticle")

                val mainController = MainController(router)
                val mainVerticle = MainVerticle(mainController)

                vertx.deployVerticle(mainVerticle, DeploymentOptions().apply {
                    this.config = config
                })
            }
        }
    }
}
