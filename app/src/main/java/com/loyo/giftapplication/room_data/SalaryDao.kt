package com.loyo.giftapplication.room_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.Year

@Dao
interface SalaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salary: Salary):Long

    @Query("select * from salary order by yearAndMonth desc")
    fun queryAll(): LiveData<MutableList<Salary>>

    @Query("select sum(monthSalary)from salary")
    fun queryAll2(): LiveData<Int>

//    @Query("select sum(monthSalary),count(yearAndMonth)  from salary where yearAndMonth/100==:year")
//    fun queryYearAll(year:Int):LiveData<QueryResult>

}