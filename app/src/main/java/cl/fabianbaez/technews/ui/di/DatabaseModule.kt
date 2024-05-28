package cl.fabianbaez.technews.ui.di

import android.content.Context
import androidx.room.Room
import cl.fabianbaez.technews.data.local.database.DatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): DatabaseBuilder {
        return Room.databaseBuilder(
            appContext,
            DatabaseBuilder::class.java,
            "news_db"
        ).build()
    }
}
