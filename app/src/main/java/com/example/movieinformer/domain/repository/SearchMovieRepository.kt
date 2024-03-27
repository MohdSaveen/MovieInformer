package com.example.movieinformer.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.movieinformer.data.local.searchMovies.searchMoviesList.SearchMovieEntity
import kotlinx.coroutines.flow.Flow


interface SearchMovieRepository {

    fun getPagingData(query : String): Pager<Int, SearchMovieEntity>

     suspend fun clearPagingData()

     suspend fun clearRemoteKeys()
}