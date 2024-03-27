package com.example.movieinformer.domain.useCases

import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieById @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository) {

     suspend operator fun invoke(id : Int): Flow<MoviesEntity> {
         return movieDetailsRepository.getMovieDetailsById(id)
     }

}