package tech.fadlan.moviecloneapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tech.fadlan.moviecloneapp.BuildConfig
import tech.fadlan.moviecloneapp.data.remote.TMDBService

@Module
@InstallIn(SingletonComponent::class)
object TMDBNetworkModule {

    @Provides
    fun providesRetro(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.Authorization_Token)
                    .build()
                    .let(chain::proceed)
            }
            .build()


        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providesRetroService(retrofit: Retrofit): TMDBService =
        retrofit.create(TMDBService::class.java)
}