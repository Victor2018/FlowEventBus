package com.cherry.floweventbus

import android.app.Application
import com.cherry.library.floweventbus.EventBusInitializer

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: App
 * Author: Victor
 * Date: 2025/11/24 10:59
 * Description: 
 * -----------------------------------------------------------------
 */

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        EventBusInitializer.init(this)
    }
}