package com.example.android_14_compose_app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cat")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val owner: String,
    val tags: List<String>,
    val createdAt: String,
    val updateAt: String,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean
)
