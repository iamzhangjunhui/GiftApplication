package com.loyo.giftapplication.utils;

class FastClick() {

    companion object {
        var lastClickTime: Long = 0
        fun click(clickInterval: Int): Boolean {
            val result= System.currentTimeMillis() - lastClickTime <= clickInterval
            lastClickTime = System.currentTimeMillis()
            return result

        }
    }





}
