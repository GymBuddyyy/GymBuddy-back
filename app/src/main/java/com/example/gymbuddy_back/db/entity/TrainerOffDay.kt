package com.example.gymbuddy_back.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainer_off_days")
data class TrainerOffDay(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trainerId: Long,
    val date: String, // YYYY-MM-DD 형식
    val time: String  // HH:mm 형식
)
