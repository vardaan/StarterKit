package com.mobiledev.starterkit.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mobiledev.starterkit.data.local.KeyValueStore
import com.mobiledev.starterkit.data.local.SharedPrefsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideKeyValueStore(prefs: SharedPreferences): KeyValueStore =
        SharedPrefsStore(prefs)

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
//        Room.databaseBuilder(ctx, AppDatabase::class.java, "app.db").build()

}
