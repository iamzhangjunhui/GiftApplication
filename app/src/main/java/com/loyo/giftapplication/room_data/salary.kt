package com.loyo.giftapplication.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Salary(@PrimaryKey val yearAndMonth: Int, val monthSalary: Int)