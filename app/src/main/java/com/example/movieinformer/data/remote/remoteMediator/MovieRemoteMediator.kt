package com.example.movieinformer.data.remote.remoteMediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieinformer.data.local.MovieDatabase
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys.RemoteKeys
import com.example.movieinformer.data.mappers.toMovieEntity
import com.example.movieinformer.data.remote.MovieApi
import com.example.movieinformer.data.remote.popularMoviesDTOs.ResponseDto
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDb: MovieDatabase,
    private val movieApi: MovieApi
): RemoteMediator<Int, MoviesEntity>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1,TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (movieDb.remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout){
            InitializeAction.SKIP_INITIAL_REFRESH
        }else{
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int,
                MoviesEntity>): MediatorResult {
        val page : Int =  when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }
        }
        try{
            val apiResponse : ResponseDto = movieApi.getMovies(
                page = page
            )
             Log.d("Savee", "load: $apiResponse")

            val movies = apiResponse.results
            val endOfPaginatedReached = movies.isEmpty()

            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH){
                    movieDb.remoteKeysDao.clearAllRemoteKeys()
                    movieDb.dao.clearAll()


                }
                val prevKey = if (page > 1) page-1 else null
                val nextKey = if (endOfPaginatedReached)null else page+1
                val remoteKeys = movies.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey, currentPage = page)
                }

                movieDb.remoteKeysDao.insertKeys(remoteKeys)
                movieDb.dao.upsertAllMovies(movies.onEach { resultDto -> resultDto.page = page }.map { it.toMovieEntity() })


            }

            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginatedReached
            )

        }catch (e: HttpException){
           return MediatorResult.Error(e)
        }catch (e:IOException){
           return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MoviesEntity>): RemoteKeys? {
        return state.pages.lastOrNull{
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { moviesEntity ->
            movieDb.remoteKeysDao.getRemoteKeysByMovieId(moviesEntity.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MoviesEntity>): RemoteKeys? {
        return state.pages.firstOrNull{
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { moviesEntity ->
            movieDb.remoteKeysDao.getRemoteKeysByMovieId(moviesEntity.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MoviesEntity>): RemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieDb.remoteKeysDao.getRemoteKeysByMovieId(id)
            }
        }
    }
}