package com.cherry.floweventbus

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cherry.floweventbus.databinding.ActivitySecondBinding
import com.cherry.floweventbus.event.GlobalEvent
import com.cherry.library.floweventbus.observe.observeEvent
import com.cherry.library.floweventbus.post.postEvent

class SecondActivity : AppCompatActivity() {
    val TAG = "SecondActivity"

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //粘性事件
        observeEvent<GlobalEvent>(isSticky = true) {
            Log.d(TAG, "SecActivity received ActivityEvent isSticky :${it.name}")
        }

        binding.sendCustomEvent.setOnClickListener {
            postEvent(GlobalEvent(name = "Hello SharedFlow now"))
        }

        binding.sendCustomDelayEvent.setOnClickListener {
            postEvent(GlobalEvent(name = "Hello SharedFlow delay"), 1000)
        }

        binding.send100Event.setOnClickListener {
            for (i in 1..100) {
                postEvent(GlobalEvent(name = "Hello SharedFlow now $i"))
            }
        }
    }
}