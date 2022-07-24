package ru.spartak.surfandroidschool.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.spartak.surfandroidschool.data.database.picture_database.dao.PictureDao
import ru.spartak.surfandroidschool.data.database.picture_database.db.PictureDatabase
import ru.spartak.surfandroidschool.domain.mapper.PictureMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PictureDatabaseModule {

    @Provides
    @Singleton
    fun providePictureDatabase(@ApplicationContext appContext: Context): PictureDatabase {
        return Room.databaseBuilder(
            appContext,
            PictureDatabase::class.java,
            "picture_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePictureDao(pictureDatabase: PictureDatabase): PictureDao {
        return pictureDatabase.pictureDao()
    }

    @Provides
    @Singleton
    fun providePictureMapper(): PictureMapper {
        return PictureMapper()
    }
}