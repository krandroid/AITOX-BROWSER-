package com.aitox.browser.di
import android.content.Context
import com.aitox.browser.data.local.AitoxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = AitoxDatabase.getDatabase(context)
    @Provides
    fun provideBookmarkDao(db: AitoxDatabase) = db.bookmarkDao()
    @Provides
    fun provideHistoryDao(db: AitoxDatabase) = db.historyDao()
}
