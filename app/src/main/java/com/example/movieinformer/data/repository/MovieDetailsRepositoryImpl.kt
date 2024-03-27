package com.example.movieinformer.data.repository

import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MovieDao
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesDao
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow


class MovieDetailsRepositoryImpl(private val movieDao : MovieDao, private val upcomingMoviesDao: UpcomingMoviesDao) : MovieDetailsRepository {
    override suspend fun getMovieDetailsById(id: Int):Flow<MoviesEntity> {
        return movieDao.getMovieById(id)
    }

    override suspend fun getUpcomingMovieDetailsById(id: Int): Flow<UpcomingMoviesEntity> {
        return upcomingMoviesDao.getMovieById(id)
    }


}