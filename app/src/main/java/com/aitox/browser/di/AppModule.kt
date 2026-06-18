package com.aitox.browser.di
import android.content.Context
import androidx.room.Room
import com.aitox.browser.data.local.BrowserDatabase
import com.aitox.browser.data.repository.BrowserRepository
import com.aitox.browser.core.manager.TabManager
import com.aitox.browser.core.webview.AdBlocker
import com.aitox.browser.core.webview.AitoxWebViewFactory
import com.aitox.browser.core.webview.DownloadHelper
import com.aitox.browser.core.webview.LongPressHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) object AppModule {
    @Provides @Singleton fun provideDatabase(@ApplicationContext context: Context): BrowserDatabase = Room.databaseBuilder(context, BrowserDatabase::class.java, "aitox_browser.db").fallbackToDestructiveMigration().build()
    @Provides @Singleton fun provideBrowserRepository(db: BrowserDatabase): BrowserRepository = BrowserRepository(db.historyDao(), db.bookmarkDao())
    @Provides @Singleton fun provideTabManager(): TabManager = TabManager()
    @Provides @Singleton fun provideWebViewFactory(@ApplicationContext context: Context): AitoxWebViewFactory = AitoxWebViewFactory(context)
    @Provides @Singleton fun provideAdBlocker(): AdBlocker = AdBlocker()
    @Provides @Singleton fun provideDownloadHelper(@ApplicationContext context: Context): DownloadHelper = DownloadHelper(context)
    @Provides @Singleton fun provideLongPressHandler(): LongPressHandler = LongPressHandler()
}
