package com.jupemo.finance.config

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.zalando.logbook.Logbook
import org.zalando.logbook.core.DefaultHttpLogFormatter
import org.zalando.logbook.core.DefaultHttpLogWriter
import org.zalando.logbook.core.DefaultSink
import org.zalando.logbook.core.DefaultStrategy

@Factory
class LogbookConfig {

    @Singleton
    fun logbook(): Logbook {
        val sink = DefaultSink(DefaultHttpLogFormatter(), DefaultHttpLogWriter())
        return Logbook.builder()
            .strategy(DefaultStrategy())
            .sink(sink)
            .build()
    }
}