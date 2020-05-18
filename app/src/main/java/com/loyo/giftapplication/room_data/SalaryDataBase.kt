package com.loyo.giftapplication.room_data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(version = 3, entities = [Salary::class])
abstract class SalaryDataBase : RoomDatabase() {
    abstract fun getSalaryDao(): SalaryDao
}