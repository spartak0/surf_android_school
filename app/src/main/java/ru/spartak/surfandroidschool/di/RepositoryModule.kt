package ru.spartak.surfandroidschool.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.database.user_database.dao.UserDao
import ru.spartak.surfandroidschool.data.network.api.RetrofitApi
import ru.spartak.surfandroidschool.data.repository.PictureRepositoryImpl
import ru.spartak.surfandroidschool.data.repository.UserRepositoryImpl
import ru.spartak.surfandroidschool.domain.UserSharedPreferenceHelper
import ru.spartak.surfandroidschool.domain.mapper.PictureMapper
import ru.spartak.surfandroidschool.domain.mapper.UserMapper
import ru.spartak.surfandroidschool.domain.repository.PictureRepository
import ru.spartak.surfandroidschool.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesUserRepository(
        userDao: UserDao,
        userMapper: UserMapper,
        api: RetrofitApi,
        sharedPreferenceHelper: UserSharedPreferenceHelper
    ): UserRepository {
        return UserRepositoryImpl(userDao, userMapper, sharedPreferenceHelper, api)
    }

    @Provides
    @Singleton
    fun providesPictureRepository(
        pictureDao: PictureDao,
        pictureMapper: PictureMapper,
        api: RetrofitApi, userSharedPreferenceHelper: UserSharedPreferenceHelper
    ): PictureRepository {
        return PictureRepositoryImpl(pictureDao, pictureMapper, api, userSharedPreferenceHelper)
    }
}