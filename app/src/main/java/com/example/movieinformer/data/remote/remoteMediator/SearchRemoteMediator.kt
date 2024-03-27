package com.example.movieinformer.data.remote.remoteMediator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieinformer.data.local.MovieDatabase
import com.example.movieinformer.data.local.searchMovies.searchMoviesList.SearchMovieEntity
import com.example.movieinformer.data.local.searchMovies.searchMoviesRemoteKeys.SearchRemoteKeys
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys.UpcomingMoviesRemoteKeysEntity
import com.example.movieinformer.data.mappers.toSearchMoviesEntity
import com.example.movieinformer.data.remote.MovieApi
import com.example.movieinformer.data.remote.searchDTOs.SearchResponseDTO
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val query: String,
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi
) : RemoteMediator<Int, SearchMovieEntity>(){

    override suspend fun initialize(): InitializeAction {

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1,TimeUnit.MILLISECONDS)

        return if (System.currentTimeMillis() - (movieDatabase.searchRemoteKeysDao.getCreationTime() ?: 0) < cacheTimeout){
            InitializeAction.SKIP_INITIAL_REFRESH
        }else{
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchMovieEntity>
    ): MediatorResult {

        val page  = when(loadType){
            LoadType.REFRESH -> 1

            LoadType.PREPEND ->{
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        try {
                val apiResponse: SearchResponseDTO = movieApi.getSearchedList(
                        query = query,
                        page = page
                    )
                val movies = apiResponse.results
                val endOfPaginatedReached = movies.isEmpty()
                movieDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDatabase.searchRemoteKeysDao.clearAllRemoteKeys()
                        movieDatabase.searchDao.clearAll()
                    }

                    val sortedMovies =movies.sortedBy { it.page }

                    val prevKey = if (page > 1 )page-1 else null
                    val nextKey = if (endOfPaginatedReached)null else page+1
                    val remoteKeys = movies.map {
                        SearchRemoteKeys(id = it.id, prevKey= prevKey, nextKey = nextKey, currentPage = page)
                    }

                    movieDatabase.searchRemoteKeysDao.insertKeys(remoteKeys)
                    movieDatabase.searchDao.upsertAllMovies(sortedMovies.onEach {result -> result.page = page }.map { it.toSearchMoviesEntity() })
                }

                return MediatorResult.Success(
                    endOfPaginationReached =endOfPaginatedReached
                )

        }catch (e : HttpException){
            return MediatorResult.Error(e)
        }catch (e : IOException){
            return MediatorResult.Error(e)
        }catch (e : Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchMovieEntity>): SearchRemoteKeys? {
        return state.pages.lastOrNull{
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { searchMovieEntity ->
            movieDatabase.searchRemoteKeysDao.getRemoteKeysByMovieId(searchMovieEntity.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchMovieEntity>): SearchRemoteKeys? {
        return state.pages.firstOrNull{
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { searchMovieEntity ->
            movieDatabase.searchRemoteKeysDao.getRemoteKeysByMovieId(searchMovieEntity.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SearchMovieEntity>): SearchRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieDatabase.searchRemoteKeysDao.getRemoteKeysByMovieId(id)
            }
        }
    }

}