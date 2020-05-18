package com.loyo.giftapplication.room_data;

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

public class DataBaseUtil {
    companion object {
        private val migration_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS Salary ")
                database.execSQL("CREATE TABLE  Salary('yearAndMonth' INTEGER PRIMARY KEY NOT NULL,'monthSalary' INTEGER NOT NULL)")
            }

        }
        fun getSalaryDao(context: Context): SalaryDao {
            return Room.databaseBuilder(context, SalaryDataBase::class.java, "datas").addMigrations(migration_3).build().getSalaryDao()
        }
    }
}
