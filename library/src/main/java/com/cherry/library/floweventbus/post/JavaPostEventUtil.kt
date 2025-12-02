package com.cherry.library.floweventbus.post

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.cherry.library.floweventbus.core.EventBusCore
import com.cherry.library.floweventbus.store.ApplicationScopeViewModelProvider

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: JavaPostEventUtil
 * Author: Victor
 * Date: 2025/12/2 10:17
 * Description: 
 * -----------------------------------------------------------------
 */

object JavaPostEventUtil {
    // 全局事件 - Java友好版本
    @JvmStatic
    @JvmOverloads
    fun <T> postEvent(event: T, timeMillis: Long = 0L) {
        ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
            .postEvent(event!!::class.java.name, event, timeMillis)
    }

    // 带Class参数的版本（Java需要）
    @JvmStatic
    fun <T> postEvent(event: T,clazz: Class<T>, timeMillis: Long = 0L) {
        ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
            .postEvent(clazz.name, event!!, timeMillis)
    }

    // 限定范围事件 - Java友好版本
    @JvmStatic
    @JvmOverloads
    fun <T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
        ViewModelProvider(scope).get(EventBusCore::class.java)
            .postEvent(event!!::class.java.name, event, timeMillis)
    }

    // 带Class参数的版本（Java需要）
    @JvmStatic
    fun <T> postEvent(scope: ViewModelStoreOwner, clazz: Class<T>, event: T, timeMillis: Long = 0L) {
        ViewModelProvider(scope).get(EventBusCore::class.java)
            .postEvent(clazz.name, event!!, timeMillis)
    }
}