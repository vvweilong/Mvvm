package com.yezao.mvvm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class UserInfoViewModel : ViewModel() {


    //初始化 livedata
    val liveData : MutableLiveData<UserInfo> by lazy {
        MutableLiveData<UserInfo>()
    }


    fun loadUserInfo(){
        liveData.value= UserInfo("default",99)
    }

    //增删改查
    fun saveUserInfo(info:UserInfo){
    }

    public fun updateName(name:String){
        val oldValue = liveData.value
        liveData.value?.name=name
        //        liveData.value= UserInfo(name, oldValue?.age!!)
    }

    fun  updateAge(age:Int){
        liveData.value?.age=age
    }





}