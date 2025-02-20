package com.example.gymbuddy_back.db.repository

import com.example.gymbuddy_back.db.dao.TrainerOffDayDAO
import com.example.gymbuddy_back.db.entity.TrainerOffDay


class TrainerOffDayRepository(private val trainerOffDayDao: TrainerOffDayDAO) {
    suspend fun insertTrainerOffDay(trainerOffDay: TrainerOffDay) {
        trainerOffDayDao.insertTrainerOffDay(trainerOffDay)
    }

    suspend fun getTrainerOffDays(trainerId: Long, date: String): List<TrainerOffDay> {
        return trainerOffDayDao.getTrainerOffDays(trainerId, date)
    }
}
