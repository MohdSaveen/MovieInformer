package com.example.movieinformer.presentation.upcomingMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.data.mappers.toUpcomingMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    pager: Pager<Int, UpcomingMoviesEntity>
): ViewModel() {

    val moviePagingFlow = pager.flow.map { paging ->
        paging.map { it.toUpcomingMovie() }
    }.cachedIn(viewModelScope)


}