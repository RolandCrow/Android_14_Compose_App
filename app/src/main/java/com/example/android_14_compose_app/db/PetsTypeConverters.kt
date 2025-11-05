package com.example.android_14_compose_app.db

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class PetsTypeConverters {

    @TypeConverter
    fun convertTagsToString(tags: List<String>): String {
        return Json.encodeToString(tags)
    }

    @TypeConverter
    fun convertStringToTags(tags: String): List<String> {
        return Json.decodeFromString(tags)
    }
}