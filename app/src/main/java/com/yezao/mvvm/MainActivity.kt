package com.yezao.mvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //创建viewmodel对象
         val userInfoViewModel= ViewModelProviders.of(this).get(UserInfoViewModel::class.java)
        //设置监听回调
        userInfoViewModel?.liveData?.observe(this, observerOfUserInfo)

        userInfoViewModel?.loadUserInfo()
    }

    val observerOfUserInfo = Observer<UserInfo> { t ->
        username.text = t?.name
        userage.text = t?.age.toString()
    }



    open fun randomInfo(v:View){
        val random=Random()
        val userInfoViewModel= ViewModelProviders.of(this).get(UserInfoViewModel::class.java)
        userInfoViewModel?.updateAge(random.nextInt(90))
        userInfoViewModel?.updateName(random.nextInt().toString())
        observerOfUserInfo.onChanged(userInfoViewModel?.liveData?.value)
    }







}
