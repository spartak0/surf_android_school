package ru.spartak.surfandroidschool.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.spartak.surfandroidschool.data.SyncUpManager
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.network.api.RetrofitApi
import ru.spartak.surfandroidschool.domain.mapper.PictureMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://pictures.chronicker.fun/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }
}