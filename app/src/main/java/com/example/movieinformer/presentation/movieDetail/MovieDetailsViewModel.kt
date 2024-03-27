package com.example.movieinformer.presentation.movieDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinformer.domain.repository.MovieDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _uiState = MutableStateFlow(MovieDetailsState())
    val uiState : StateFlow<MovieDetailsState> = _uiState.asStateFlow()

   init {

      viewModelScope.launch {
          val movieId = savedStateHandle.get<Int>("movieId")
          val flag = savedStateHandle.get<Boolean>("flag")
          if (flag == true) {
              if (movieId != null) {
                  movieDetailsRepository.getMovieDetailsById(movieId).collect { movieEntity ->
                      _uiState.update {
                          it.copy(
                              original_title = movieEntity.original_title,
                              original_language = movieEntity.original_language,
                              overview = movieEntity.overview,
                              release_date = movieEntity.release_date,
                              id = movieEntity.id,
                              title = movieEntity.title,
                              backdrop_path = movieEntity.backdrop_path,
                              vote_average = movieEntity.vote_average,
                              vote_count = movieEntity.vote_count,
                              genre_ids = movieEntity.genre_ids
                          )
                      }
                  }
              }
          }else if (flag == false){
              if (movieId != null){
                  movieDetailsRepository.getUpcomingMovieDetailsById(movieId).collect {movieEntity ->
                      _uiState.update {
                          it.copy(
                              original_title = movieEntity.original_title,
                              original_language = movieEntity.original_language,
                              overview = movieEntity.overview,
                              release_date = movieEntity.release_date,
                              id = movieEntity.id,
                              title = movieEntity.title,
                              backdrop_path = movieEntity.backdrop_path,
                              vote_average = movieEntity.vote_average,
                              vote_count = movieEntity.vote_count,
                              genre_ids = movieEntity.genre_ids
                          )
                      }
                  }
              }

          }

      }
   }










}