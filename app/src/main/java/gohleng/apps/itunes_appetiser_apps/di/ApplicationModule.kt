package gohleng.apps.itunes_appetiser_apps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import gohleng.apps.itunes_appetiser_apps.data.local.AppDatabase
import gohleng.apps.itunes_appetiser_apps.data.local.TrackDao
import gohleng.apps.itunes_appetiser_apps.data.remote.TrackService
import gohleng.apps.itunes_appetiser_apps.data.repository.TrackRepository
import gohleng.apps.itunes_appetiser_apps.test.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {
    @Provides
    internal fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    internal fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Provides
    fun provideTrackService(retrofit: Retrofit): TrackService = retrofit.create(
        TrackService::class.java
    )

    @Provides
    fun provideTrackDao(db: AppDatabase) = db.trackDao()

    @Provides
    fun provideRepository(remote: TrackService, local: TrackDao) = TrackRepository(remote, local)
}