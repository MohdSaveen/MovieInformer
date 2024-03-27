package com.example.movieinformer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movieinformer.data.local.MovieDatabase
import com.example.movieinformer.data.local.searchMovies.searchMoviesList.SearchMovieEntity
import com.example.movieinformer.data.remote.MovieApi
import com.example.movieinformer.data.remote.remoteMediator.SearchRemoteMediator
import com.example.movieinformer.domain.repository.SearchMovieRepository
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(

    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase,
) : SearchMovieRepository{

    private val searchDao = movieDatabase.searchDao
    private val searchRemoteKeys = movieDatabase.searchRemoteKeysDao

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingData(query: String): Pager<Int, SearchMovieEntity> {

        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 10,
                initialLoadSize = 25
            ),
            remoteMediator = SearchRemoteMediator(query, movieDatabase, movieApi),
            pagingSourceFactory = { searchDao.pagingSource() }
        )
    }

    override suspend fun clearPagingData() {
        return searchDao.clearAll()
    }

    override suspend fun clearRemoteKeys() {
        return searchRemoteKeys.clearAllRemoteKeys()
    }


}