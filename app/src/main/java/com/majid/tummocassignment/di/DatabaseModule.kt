package com.majid.tummocassignment.di

import android.content.Context
import androidx.room.Room
import com.majid.tummocassignment.data.database.TummocDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTummocDB( @ApplicationContext context: Context) : TummocDB {
        return  Room.databaseBuilder(context, TummocDB::class.java,"TummocDB").build()
    }
}