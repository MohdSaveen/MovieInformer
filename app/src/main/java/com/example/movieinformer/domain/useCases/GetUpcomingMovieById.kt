package com.example.movieinformer.domain.useCases

import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetUpcomingMovieById @Inject constructor(private val repository: MovieDetailsRepository){
    suspend operator fun invoke(id : Int) : Flow<UpcomingMoviesEntity>{
        return repository.getUpcomingMovieDetailsById(id)

    }
}
