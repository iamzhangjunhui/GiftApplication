package com.loyo.giftapplication.ui.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyo.giftapplication.room_data.*

class DashboardViewModel(val context: Application) : AndroidViewModel(context) {

    fun getTotal(): LiveData<QueryResult> {
        return DataBaseUtil.getSalaryDao(context).queryAll2()
    }

    fun getYearTotal(year: Int): LiveData<QueryResult> {
        return DataBaseUtil.getSalaryDao(context).queryYearAll(year)
    }

    fun getDefineMonthAll(startYearMonth: Int, endYearMonth: Int): LiveData<QueryResult> {
        return DataBaseUtil.getSalaryDao(context).queryDefineMonthAll(startYearMonth, endYearMonth)
    }

    fun getYearBonus(year: Int): LiveData<Int> {
        return DataBaseUtil.getSalaryDao(context).queryYearBonus(year)
    }

    fun getTotalBonus(): LiveData<YearBonusResult> {
        return DataBaseUtil.getSalaryDao(context).queryTotalBonus()
    }

    fun getTotalBonusMaxMin(): LiveData<YearBonusMinMaxResult> {
        return DataBaseUtil.getSalaryDao(context).queryTotalMinMaxBonus()
    }

}