package com.cherry.floweventbus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.cherry.floweventbus.databinding.ActivityMainBinding
import com.cherry.floweventbus.databinding.ContentMainBinding
import com.cherry.library.floweventbus.event.ActivityEvent
import com.cherry.library.floweventbus.event.GlobalDataEvent
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
            val data = UserInfo()
            data.name = "victor"
            data.sex = "男"
            data.mail = "victor423099@gmail.com"
            data.address = "广东省深圳市宝安区渔业旧村一巷198号"
            postEvent(GlobalDataEvent("has_data_event",data))
        }

        mContentMainBinding.openSec.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    //跨页面
    private fun observeGlobalEvents() {
        //全局事件
        observeEvent<GlobalEvent> { value ->
            Log.d(TAG, "MainActivity received GlobalEvent1  :${value.event}")
            mContentMainBinding.mTvActivityResult.text = value.event
        }
        observeEvent<GlobalDataEvent<UserInfo>> { value ->
            Log.d(TAG, "MainActivity received GlobalEvent2  :${value.event}")
            Log.d(TAG, "MainActivity received GlobalEvent2  :${value.data}")
            mContentMainBinding.mTvActivityResult.text =  "${value.event}-${value.data}"
        }
    }

    //本页面
    private fun observeActivityScopeEvents() {
        //Activity 级别的 事件
        //自定义事件
        observeEvent<ActivityEvent>(this) {
            Log.d(TAG, "MainActivity received ActivityEvent: ${it.event}")
            mContentMainBinding.mTvActivityResult.text = it.event
        }

//        //自定义事件 切换线程
        observeEvent<ActivityEvent>(Dispatchers.IO) {
            Log.d(TAG, "received ActivityEvent:${it.event} " + Thread.currentThread().name)
            mContentMainBinding.mTvActivityResult.text = "received ActivityEvent:${it.event} " + Thread.currentThread().name
        }
//
//        //自定义事件 指定最小生命周期
        observeEvent<ActivityEvent>(minActiveState = Lifecycle.State.DESTROYED) {
            Log.d(TAG, "received ActivityEvent:${it.event}   >  DESTROYED")
            mContentMainBinding.mTvActivityResult.text = "${it.event}   >  DESTROYED"
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