package cl.fabianbaez.technews.ui.di

import cl.fabianbaez.technews.data.Local
import cl.fabianbaez.technews.data.Remote
import cl.fabianbaez.technews.data.RepositoryImpl
import cl.fabianbaez.technews.data.local.LocalImpl
import cl.fabianbaez.technews.data.remote.RemoteImpl
import cl.fabianbaez.technews.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(data: RepositoryImpl): Repository {
        return data
    }

    @Singleton
    @Provides
    fun provideRemote(dataSourceRemote: RemoteImpl): Remote {
        return dataSourceRemote
    }

    @Singleton
    @Provides
    fun provideLocal(dataSourceLocal: LocalImpl): Local {
        return dataSourceLocal
    }

}
