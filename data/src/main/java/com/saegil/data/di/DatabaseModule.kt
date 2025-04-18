package com.saegil.data.di

import android.content.Context
import androidx.room.Room
import com.saegil.data.local.AppDatabase
import com.saegil.data.local.TokenDao
import com.saegil.data.local.TokenDataSource
import com.saegil.data.local.TokenDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTokenDao(appDatabase: AppDatabase): TokenDao {
        return appDatabase.tokenDao()
    }

    @Provides
    @Singleton
    fun provideTokenDataSource(tokenDao: TokenDao): TokenDataSource {
        return TokenDataSourceImpl(tokenDao)
    }
}
