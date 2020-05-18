package com.loyo.giftapplication.ui.salary_list

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyo.giftapplication.room_data.DataBaseUtil
import com.loyo.giftapplication.room_data.Salary

class SalarysViewModel(val context: Application) : AndroidViewModel(context) {
    fun getSalarys():LiveData<MutableList<Salary>>{
        return  DataBaseUtil.getSalaryDao(context).queryAll()
    }
}