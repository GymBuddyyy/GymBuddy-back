package com.example.gymbuddy_back.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gymbuddy_back.db.entity.TrainerOffDay


@Dao
interface TrainerOffDayDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainerOffDay(offDay: TrainerOffDay)

    @Query("SELECT * FROM trainer_off_days WHERE trainerId = :trainerId AND date = :date")
    suspend fun getTrainerOffDays(trainerId: Long, date: String): List<TrainerOffDay>


}