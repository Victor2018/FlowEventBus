package com.cherry.library.floweventbus.post

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.cherry.library.floweventbus.core.EventBusCore
import com.cherry.library.floweventbus.store.ApplicationScopeViewModelProvider

//_______________________________________
//          post event
//_______________________________________

//Application范围的事件
inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}

//限定范围的事件
inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}

// Java API (非内联版本)
fun <T> postEvent(event: T, clazz: Class<T>, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(clazz.name, event!!, timeMillis)
}

// 限定范围的事件 - Java API(非内联版本)
fun <T> postEvent(scope: ViewModelStoreOwner, event: T, clazz: Class<T>, timeMillis: Long = 0L) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .postEvent(clazz.name, event!!, timeMillis)
}