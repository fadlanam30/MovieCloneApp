package tech.fadlan.moviecloneapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.fadlan.moviecloneapp.data.repository.TMDBRepositoryImpl
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsGithubRepository(
        tmdbRepositoryImpl: TMDBRepositoryImpl
    ): TMDBRepository
}