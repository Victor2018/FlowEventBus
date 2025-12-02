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

            val data = UserInfo()
            data.name = "victor"
            data.sex = "男"
            data.mail = "victor423099@gmail.com"
            data.address = "广东省深圳市宝安区渔业旧村一巷198号"
            postEvent(GlobalEvent(data))
        }

        mContentMainBinding.openSec.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    //跨页面
    private fun observeGlobalEvents() {
        //全局事件
        observeEvent<GlobalEvent<String>> { value ->
            Log.d(TAG, "MainActivity received GlobalEvent  :${value.data}")
            mContentMainBinding.mTvActivityResult.text = value.data
        }
        observeEvent<GlobalEvent<UserInfo>> { value ->
            Log.d(TAG, "MainActivity received GlobalEvent  :${value.data}")
            mContentMainBinding.mTvActivityResult.text = value.data.toString()
        }
    }

    //本页面
    private fun observeActivityScopeEvents() {
        //Activity 级别的 事件
        //自定义事件
        observeEvent<ActivityEvent<String>>(this) {
            Log.d(TAG, "MainActivity received ActivityEvent: ${it.data}")
            mContentMainBinding.mTvActivityResult.text = it.data
        }

//        //自定义事件 切换线程
        observeEvent<ActivityEvent<String>>(Dispatchers.IO) {
            Log.d(TAG, "received ActivityEvent:${it.data} " + Thread.currentThread().name)
            mContentMainBinding.mTvActivityResult.text = "received ActivityEvent:${it.data} " + Thread.currentThread().name
        }
//
//        //自定义事件 指定最小生命周期
        observeEvent<ActivityEvent<String>>(minActiveState = Lifecycle.State.DESTROYED) {
            Log.d(TAG, "received ActivityEvent:${it.data}   >  DESTROYED")
            mContentMainBinding.mTvActivityResult.text = "${it.data}   >  DESTROYED"
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