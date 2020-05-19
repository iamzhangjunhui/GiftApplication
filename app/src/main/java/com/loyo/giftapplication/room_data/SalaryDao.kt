package com.loyo.giftapplication.room_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.Year
import java.time.YearMonth

@Dao
interface SalaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salary: Salary): Long

    @Query("select * from salary order by yearAndMonth desc")
    fun queryAll(): LiveData<MutableList<Salary>>

    @Query("select SUM(monthSalary) as totalSalary,COUNT(*) as monthNum ,min(monthSalary) as min,max(monthSalary) as max from salary where yearAndMonth%100!=13")
    fun queryAll2(): LiveData<QueryResult>

    @Query("select SUM(monthSalary) as totalBonus, COUNT(*) as bonusNum from salary where yearAndMonth%100==13")
    fun queryTotalBonus(): LiveData<YearBonusResult>

    @Query("select Min(monthSalary) as min, max(monthSalary) as max from salary where yearAndMonth%100==13")
    fun queryTotalMinMaxBonus(): LiveData<YearBonusMinMaxResult>

    @Query("select SUM(monthSalary) as totalSalary,COUNT(*) as monthNum ,min(monthSalary) as min,max(monthSalary) as max from salary where yearAndMonth/100==:year and yearAndMonth%100!=13")
    fun queryYearAll(year: Int): LiveData<QueryResult>

    @Query("select monthSalary  from salary where yearAndMonth/100==:year and yearAndMonth%100==13")
    fun queryYearBonus(year: Int): LiveData<Int>

    @Query("select SUM(monthSalary) as totalSalary,COUNT(*) as monthNum ,min(monthSalary) as min,max(monthSalary) as max from salary Where yearAndMonth>=:startYearMonth and yearAndMonth<=:endYearMonth and yearAndMonth%100!=13")
    fun queryDefineMonthAll(startYearMonth: Int, endYearMonth: Int): LiveData<QueryResult>

}