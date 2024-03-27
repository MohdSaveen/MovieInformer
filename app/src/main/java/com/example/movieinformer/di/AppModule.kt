package com.example.movieinformer.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MovieDao
import com.example.movieinformer.data.local.MovieDatabase
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys.RemoteKeysDao
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesDao
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys.UpcomingMoviesREmoteKeysDao
import com.example.movieinformer.data.remote.MovieApi
import com.example.movieinformer.data.remote.MovieApi.Companion.BASE_URL
import com.example.movieinformer.data.remote.remoteMediator.MovieRemoteMediator
import com.example.movieinformer.data.remote.remoteMediator.UpcomingMoviesRemoteMediator
import com.example.movieinformer.data.repository.MovieDetailsRepositoryImpl
import com.example.movieinformer.domain.repository.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context) : MovieDatabase{
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "Movie.Db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieApi() :MovieApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDao(movieDb: MovieDatabase) : MovieDao {
        return movieDb.dao
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(movieDb: MovieDatabase) : RemoteKeysDao {
        return movieDb.remoteKeysDao
    }

    @Singleton
    @Provides
    fun provideUpcomingMoviesDao(movieDb: MovieDatabase) : UpcomingMoviesDao{
        return movieDb.upcomingDao
    }

    @Singleton
    @Provides
    fun provideUpcomingRemoteKeys(movieDb: MovieDatabase) : UpcomingMoviesREmoteKeysDao {
        return movieDb.upcomingMoviesRemoteKeyDao
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviePager(movieDb: MovieDatabase, movieApi:MovieApi):Pager<Int, MoviesEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 10, initialLoadSize = 20),
            remoteMediator = MovieRemoteMediator(movieDb, movieApi),
            pagingSourceFactory = {movieDb.dao.pagingSource()}
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUpcomingMoviePager(movieDb: MovieDatabase, movieApi:MovieApi):Pager<Int, UpcomingMoviesEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 10, initialLoadSize = 20),
            remoteMediator = UpcomingMoviesRemoteMediator(movieDb, movieApi),
            pagingSourceFactory = {movieDb.upcomingDao.remotePagingSource()}
        )
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(movieDao: MovieDao, upcomingMoviesDao: UpcomingMoviesDao) : MovieDetailsRepository{
        return MovieDetailsRepositoryImpl(movieDao,upcomingMoviesDao )
    }

}