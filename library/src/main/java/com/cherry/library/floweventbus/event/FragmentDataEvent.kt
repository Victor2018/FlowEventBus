package com.cherry.library.floweventbus.event

data class FragmentDataEvent<T>(val event: String, val data: T? = null)