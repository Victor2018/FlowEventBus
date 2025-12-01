package com.cherry.floweventbus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.cherry.floweventbus.databinding.ActivityMainBinding
import com.cherry.floweventbus.databinding.ContentMainBinding
import com.cherry.library.floweventbus.event.ActivityEvent
import com.cherry.library.floweventbus.event.GlobalEvent
import com.cherry.library.floweventbus.observe.observeEvent
import com.cherry.library.floweventbus.post.postEvent
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var mContentMainBinding: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        mContentMainBinding = binding.mLlContent

        setContentView(binding.root)

        observeGlobalEvents()
        observeActivityScopeEvents()

        mContentMainBinding.sendActivityEvent.setOnClickListener {
            postEvent(this, ActivityEvent("ActivityEvent来自MainActivity"))
        }

        mContentMainBinding.sendEvent.setOnClickListener {
            postEvent(GlobalEvent("GlobalEvent来自MainActivity"))
        }

        mContentMainBinding.openSec.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    //跨页面
    private fun observeGlobalEvents() {
        //全局事件
        observeEvent<GlobalEvent> { value ->
            Log.d(TAG, "MainActivity received GlobalEvent  :${value.name}")
            mContentMainBinding.mTvActivityResult.text = value.name
        }
    }

    //本页面
    private fun observeActivityScopeEvents() {
        //Activity 级别的 事件
        //自定义事件
        observeEvent<ActivityEvent>(this) {
            Log.d(TAG, "MainActivity received ActivityEvent: ${it.name}")
            mContentMainBinding.mTvActivityResult.text = it.name
        }

//        //自定义事件 切换线程
        observeEvent<ActivityEvent>(Dispatchers.IO) {
            Log.d(TAG, "received ActivityEvent:${it.name} " + Thread.currentThread().name)
            mContentMainBinding.mTvActivityResult.text = Thread.currentThread().name
        }
//
//        //自定义事件 指定最小生命周期
        observeEvent<ActivityEvent>(minActiveState = Lifecycle.State.DESTROYED) {
            Log.d(TAG, "received ActivityEvent:${it.name}   >  DESTROYED")
            mContentMainBinding.mTvActivityResult.text = "${it.name}   >  DESTROYED"
        }

//        //自定义事件 切换线程 + 指定最小生命周期
//        observeEvent<ActivityEvent>(Dispatchers.IO, Lifecycle.State.DESTROYED) {
//            Log.d(
//                TAG,
//                "received ActivityEvent:${it.name} >  DESTROYED    " + Thread.currentThread().name
//            )
//        }
    }

}