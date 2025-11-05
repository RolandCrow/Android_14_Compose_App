package com.example.android_14_compose_app.repository

import com.example.android_14_compose_app.data.Cat
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    suspend fun getPets(): Flow<List<Cat>>
    suspend fun updatePet(cat: Cat)
    suspend fun getFavoritePets(): Flow<List<Cat>>
}