package com.loyo.giftapplication.room_data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SalaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salary: Salary)

    @Query("select * from salary")
    fun queryAll(): LiveData<List<Salary>>

}