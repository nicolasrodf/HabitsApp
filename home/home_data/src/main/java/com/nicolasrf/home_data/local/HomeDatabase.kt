package com.nicolasrf.home_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nicolasrf.home_data.local.entity.HabitEntity
import com.nicolasrf.home_data.local.entity.HabitSyncEntity
import com.nicolasrf.home_data.local.typeconverter.HomeTypeConverter

@Database(entities = [HabitEntity::class, HabitSyncEntity::class], version = 1)
@TypeConverters(
    HomeTypeConverter::class
)
abstract class HomeDatabase : RoomDatabase() {
    abstract val dao: HomeDao
}