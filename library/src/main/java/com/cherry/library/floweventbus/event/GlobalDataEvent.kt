package com.cherry.library.floweventbus.event

data class GlobalDataEvent<T>(val event: String, val data: T? = null)