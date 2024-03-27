package com.example.movieinformer.presentation.searchBar

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieinformer.data.mappers.toSearchMovie
import com.example.movieinformer.domain.model.SearchMovie
import com.example.movieinformer.domain.repository.SearchMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieRepository: SearchMovieRepository
) : ViewModel() {


    private val _searchingMovie = MutableStateFlow<PagingData<SearchMovie>>(PagingData.empty())
    val searchingMovie : StateFlow<PagingData<SearchMovie>> = _searchingMovie

    @SuppressLint("CheckResult")
    fun getSearchMovie(query: String){
            searchMovieRepository.getPagingData(query)
                .flow
                .cachedIn(viewModelScope).onEach { pagingData ->

                    //checking the data is empty or not
                    _searchingMovie.value = pagingData.map { it.toSearchMovie() }
                    Log.d("TAG", "getSearchMovie: ${_searchingMovie.value} ")
                }
                .catch { exception ->
                    Log.e("TAG", "Error fetching search movie data: $exception")
                }.launchIn(viewModelScope)

    }

    fun getSearched(query: String) : Flow<PagingData<SearchMovie>>{
            val movieSearchedFlow = searchMovieRepository.getPagingData(query).flow.map { paging->
                    paging.map { it.toSearchMovie() }
                }.cachedIn(viewModelScope)

             Log.d("TAG", "getSearched: $movieSearchedFlow")
            return movieSearchedFlow
    }

}