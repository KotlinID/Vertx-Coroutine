package id.kotlin.jasoet.covertx.module


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import dagger.Module
import dagger.Provides
import id.kotlin.jasoet.covertx.extension.logger
import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import io.vertx.core.file.FileSystem
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.templ.PebbleTemplateEngine
import io.vertx.ext.web.templ.TemplateEngine
import org.litote.kmongo.util.KMongoConfiguration
import javax.inject.Named
import javax.inject.Singleton

/**
 * VertxModule used for initialize objects required by Vertx
 *
 * @author Deny Prasetyo.
 */

@Module
class VertxModule(val vertx: Vertx) {
    private val log = logger(VertxModule::class)

    init {
        log.info("Initialize VertxModule!")
        Json.mapper.apply {
            registerKotlinModule()
            registerModule(ParameterNamesModule())
            registerModule(JavaTimeModule())
        }

        Json.prettyMapper.apply {
            registerKotlinModule()
            registerModule(ParameterNamesModule())
            registerModule(JavaTimeModule())
        }
    }

    @Provides
    fun provideRouter(): Router {
        return Router.router(vertx)
    }

    @Singleton
    @Provides
    fun provideEventBus(): EventBus {
        return vertx.eventBus()
    }

    @Provides
    @Singleton
    fun provideVertx(): Vertx {
        return vertx
    }

    @Provides
    @Singleton
    @Named("default")
    fun provideDefaultObjectMapper(): ObjectMapper {
        return Json.mapper
    }

    @Provides
    @Singleton
    @Named("extended")
    fun provideExtendedObjectMapper(): ObjectMapper {
        return KMongoConfiguration.extendedJsonMapper
    }

    @Provides
    @Singleton
    @Named("bson")
    fun provideBsonObjectMapper(): ObjectMapper {
        return KMongoConfiguration.bsonMapper
    }

    @Provides
    @Singleton
    fun provideFileSystem(): FileSystem {
        return vertx.fileSystem()
    }

    @Provides
    @Singleton
    fun providePebbleTemplate(): TemplateEngine {
        return PebbleTemplateEngine.create(vertx)
    }

}
