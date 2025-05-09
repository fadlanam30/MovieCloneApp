package tech.fadlan.moviecloneapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import tech.fadlan.moviecloneapp.core.IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DisPatcherModule {

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}