package com.example.movieinformer.domain.repository

import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun getMovieDetailsById(id : Int): Flow<MoviesEntity>

    suspend fun getUpcomingMovieDetailsById(id : Int) : Flow<UpcomingMoviesEntity>

}