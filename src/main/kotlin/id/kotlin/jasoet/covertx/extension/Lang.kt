package id.kotlin.jasoet.covertx.extension

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.core.logging.SLF4JLogDelegateFactory
import kotlin.reflect.KClass


/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo.
 */

fun logger(clz: KClass<*>): Logger {
    return LoggerFactory.getLogger(clz.qualifiedName)
}

fun useLogback(){
    System.setProperty(LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory::class.java.name)
}

@Suppress("NOTHING_TO_INLINE")
inline infix fun <T> T?.orNotFound(message: String): T {
    return if (this == null) {
        throw  NullObjectException(message)
    } else {
        this
    }
}

@Suppress("NOTHING_TO_INLINE")
inline infix fun <T> T?.orDataError(message: String): T {
    return if (this == null) {
        throw  DataInconsistentException(message)
    } else {
        this
    }
}

class NullObjectException : RuntimeException {
    constructor(message: String, ex: Exception?) : super(message, ex)

    constructor(message: String) : super(message)

    constructor(ex: Exception) : super(ex)
}

class DataInconsistentException : RuntimeException {
    constructor(message: String, ex: Exception?) : super(message, ex)

    constructor(message: String) : super(message)

    constructor(ex: Exception) : super(ex)
}


