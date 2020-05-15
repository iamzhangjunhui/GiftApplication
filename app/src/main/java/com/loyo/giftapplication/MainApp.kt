package com.loyo.giftapplication

import android.app.Application

class MainApp :Application(){
    companion object{
       lateinit var  application:Application
    }
    override fun onCreate() {
        super.onCreate()
        application=this
    }
}