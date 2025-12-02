package com.cherry.library.floweventbus.event

import android.util.Log
import androidx.fragment.app.Fragment
import com.cherry.library.floweventbus.observe.observeEvent
import com.cherry.library.floweventbus.post.postEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CustomComponent {
    val TAG = "CustomComponent"

    fun sendEvent(fragment: Fragment) {
        postEvent(GlobalEvent("funny"))
        postEvent(fragment, GlobalEvent("funny"))
        postEvent(fragment.requireActivity(), GlobalEvent("funny"))
    }

    fun observer(fragment: Fragment) {
        observeEvent<GlobalEvent<String>>(CoroutineScope(Dispatchers.Main)) {
            Log.d(TAG, "CustomComponent received GlobalEvent 3:${it.data}")
        }

        observeEvent<FragmentEvent<String>>(fragment) {
            Log.d(TAG, "CustomComponent received FragmentEvent 3:${it.data}")
        }
        observeEvent<ActivityEvent<String>>(fragment.requireActivity()) {
            Log.d(TAG, "CustomComponent received ActivityEvent 3:${it.data}")
        }
    }
}