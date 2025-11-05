package com.example.android_14_compose_app.di

import androidx.room.Room
import com.example.android_14_compose_app.api.CatsApi
import com.example.android_14_compose_app.db.CatDatabase
import com.example.android_14_compose_app.repository.PetsRepository
import com.example.android_14_compose_app.repository.PetsRepositoryImpl
import com.example.android_14_compose_app.viewmodel.PetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module{
    single<PetsRepository> { PetsRepositoryImpl(get(),get(),get()) }
    single { Dispatchers.IO }
    single { PetsViewModel(get())}
    single {
        OkHttpClient.Builder()
            .addInterceptor(SimpleInterceptor())
            .build()
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .client(get())
            .baseUrl("https://cataas.com/api/")
            .build()
    }
    single { get<Retrofit>().create(CatsApi::class.java) }
    single {
        Room.databaseBuilder(
            androidContext(),
            CatDatabase::class.java,
            "cat-database"
        ).build()
    }
    single { get<CatDatabase>().catDao() }
}

class SimpleInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("User-Agent", "MyApp/1.0")
            .build()
        val response = chain.proceed(newRequest)
        return response
    }
}