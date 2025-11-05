package com.example.android_14_compose_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CatEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(PetsTypeConverters::class)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}