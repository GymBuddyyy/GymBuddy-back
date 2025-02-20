package com.example.gymbuddy_back.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymbuddy_back.db.dao.TrainerOffDayDAO
import com.example.gymbuddy_back.db.entity.TrainerOffDay


@Database(entities = [TrainerOffDay::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainerOffDayDao(): TrainerOffDayDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymbuddy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
