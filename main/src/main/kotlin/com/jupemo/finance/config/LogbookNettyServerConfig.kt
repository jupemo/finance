package com.jupemo.finance.config

import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.http.netty.channel.ChannelPipelineCustomizer
import io.micronaut.http.server.netty.NettyServerCustomizer
import io.netty.channel.Channel
import jakarta.inject.Singleton
import org.zalando.logbook.Logbook
import org.zalando.logbook.netty.LogbookServerHandler

@Requires(beans = [Logbook::class])
@Singleton
class LogbookNettyServerConfig(private val logbook: Logbook) :
    BeanCreatedEventListener<NettyServerCustomizer.Registry> {
    override fun onCreated(event: BeanCreatedEvent<NettyServerCustomizer.Registry>): NettyServerCustomizer.Registry {
        val registry = event.bean
        registry.register(Customizer(null))
        return registry
    }

    private inner class Customizer(private val channel: Channel?) : NettyServerCustomizer {

        override fun specializeForChannel(channel: Channel, role: NettyServerCustomizer.ChannelRole) = Customizer(channel)

        override fun onStreamPipelineBuilt() {
            channel!!.pipeline()
                .addAfter(
                    ChannelPipelineCustomizer.HANDLER_HTTP_SERVER_CODEC,
                    "logbook",
                    LogbookServerHandler(logbook)
                )
        }

    }


}