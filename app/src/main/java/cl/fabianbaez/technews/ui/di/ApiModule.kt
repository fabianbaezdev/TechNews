package cl.fabianbaez.technews.ui.di

import cl.fabianbaez.technews.data.remote.retrofit.Webservice
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Reusable
    @Provides
    fun provideApi(retrofit: Retrofit): Webservice {
        return retrofit.create(Webservice::class.java)
    }
}
