package com.example.android_14_compose_app.repository

import com.example.android_14_compose_app.api.CatsApi
import com.example.android_14_compose_app.data.Cat
import com.example.android_14_compose_app.db.CatDao
import com.example.android_14_compose_app.db.CatEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PetsRepositoryImpl(
    private val catsApi: CatsApi,
    private val dispatcher: CoroutineDispatcher,
    private val catDao: CatDao
): PetsRepository {
    override suspend fun getPets(): Flow<List<Cat>> {
        return withContext(dispatcher) {
            val response = catsApi.fetchCats("cute")
            if(response.isSuccessful) {
                response.body()?.map {
                    catDao.insert(
                        CatEntity(
                            id = it.id,
                            owner = it.owner,
                            tags = it.tags,
                            createdAt = it.createdAt,
                            updateAt = it.updateAt,
                            isFavorite = it.isFavorite
                        )
                    )
                }
            }
            catDao.getCats()
                .map { petsCached ->
                    petsCached.map { catEntity ->
                        Cat(
                            id = catEntity.id,
                            owner = catEntity.owner,
                            tags = catEntity.tags,
                            createdAt = catEntity.createdAt,
                            updateAt = catEntity.updateAt,
                            isFavorite = catEntity.isFavorite
                        )
                    }
                }
        }
    }

    override suspend fun updatePet(cat: Cat) {
        withContext(dispatcher) {
            catDao.update(
                CatEntity(
                    id = cat.id,
                    owner = cat.owner,
                    tags = cat.tags,
                    createdAt = cat.createdAt,
                    updateAt = cat.updateAt,
                    isFavorite = cat.isFavorite
                )
            )
        }
    }

    override suspend fun getFavoritePets(): Flow<List<Cat>> {
        return withContext(dispatcher)  {
            catDao.getFavoriteCats()
                .map { petsCached ->
                    petsCached.map { catEntity ->
                        Cat(
                            id = catEntity.id,
                            owner = catEntity.owner,
                            tags = catEntity.tags,
                            createdAt = catEntity.createdAt,
                            updateAt = catEntity.updateAt,
                            isFavorite = catEntity.isFavorite
                        )
                    }
                }
        }
    }
}