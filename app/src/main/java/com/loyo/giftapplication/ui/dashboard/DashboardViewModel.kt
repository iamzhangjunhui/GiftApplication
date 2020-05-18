package com.loyo.giftapplication.ui.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyo.giftapplication.room_data.DataBaseUtil
import com.loyo.giftapplication.room_data.QueryResult
import com.loyo.giftapplication.room_data.Salary
import com.loyo.giftapplication.room_data.SalaryDao

class DashboardViewModel(val context: Application) : AndroidViewModel(context) {
//    fun getYearTotal(year: Int): LiveData<QueryResult> {
//        return DataBaseUtil.getSalaryDao(context).queryYearAll(year)
//    }
    fun getTotal(): LiveData<Int> {
        return DataBaseUtil.getSalaryDao(context).queryAll2()
    }

}