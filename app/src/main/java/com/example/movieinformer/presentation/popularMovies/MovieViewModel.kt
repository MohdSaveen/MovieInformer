package com.example.movieinformer.presentation.popularMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.mappers.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    pager: Pager<Int, MoviesEntity>,

    ) :ViewModel(){

    val moviePagingFlow = pager.flow.map { paging ->
        paging.map { it.toMovie() }
    }.cachedIn(viewModelScope)



}