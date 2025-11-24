package com.cherry.library.floweventbus

import android.app.Application
import com.cherry.library.floweventbus.util.ILogger

object EventBusInitializer {

    lateinit var application: Application

    var logger: ILogger? = null

    fun init(application: Application, logger: ILogger? = null) {
        EventBusInitializer.application = application
        this.logger = logger
    }

}