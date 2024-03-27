package com.example.movieinformer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MovieDao
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys.RemoteKeys
import com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys.RemoteKeysDao
import com.example.movieinformer.data.local.searchMovies.searchMoviesList.SearchMoviesDao
import com.example.movieinformer.data.local.searchMovies.searchMoviesList.SearchMovieEntity
import com.example.movieinformer.data.local.searchMovies.searchMoviesRemoteKeys.SearchRemoteKeysDao
import com.example.movieinformer.data.local.searchMovies.searchMoviesRemoteKeys.SearchRemoteKeys
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesDao
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys.UpcomingMoviesREmoteKeysDao
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys.UpcomingMoviesRemoteKeysEntity
import com.example.movieinformer.domain.utils.Converter

@Database(entities = [MoviesEntity::class,
     RemoteKeys::class,
     UpcomingMoviesEntity::class,
     UpcomingMoviesRemoteKeysEntity::class,
                     SearchMovieEntity::class,
                     SearchRemoteKeys::class],
    version = 1)
@TypeConverters(Converter::class)
abstract class MovieDatabase :RoomDatabase() {

     abstract val dao : MovieDao
     abstract val remoteKeysDao : RemoteKeysDao

     //upcoming
     abstract val upcomingDao :UpcomingMoviesDao
     abstract val upcomingMoviesRemoteKeyDao :UpcomingMoviesREmoteKeysDao

     //searching
     abstract val searchDao : SearchMoviesDao
     abstract val searchRemoteKeysDao : SearchRemoteKeysDao

}