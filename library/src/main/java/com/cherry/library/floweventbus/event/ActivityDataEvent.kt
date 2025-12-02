package com.cherry.library.floweventbus.event

data class ActivityDataEvent<T>(val event: String, val data: T? = null)