package com.example.movieinformer.data.remote.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MovieDao
import com.example.movieinformer.data.local.MovieDatabase
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys.RemoteKeys
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesDao
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys.UpcomingMoviesRemoteKeysEntity
import com.example.movieinformer.data.mappers.toUpcomingMovieEntity
import com.example.movieinformer.data.remote.MovieApi
import com.example.movieinformer.data.remote.upcomingMoviesDTOs.UpcomingResponseDTO
import okio.IOException
import okio.Timeout
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class UpcomingMoviesRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi
) : RemoteMediator<Int, UpcomingMoviesEntity>() {

    override suspend fun initialize(): InitializeAction {

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1,TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (movieDatabase.upcomingMoviesRemoteKeyDao.getCreationTime() ?: 0) < cacheTimeout){
            InitializeAction.SKIP_INITIAL_REFRESH
        }else{
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UpcomingMoviesEntity>
    ): MediatorResult {

        val page : Int = when(loadType){
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
        try {
            val apiResponse : UpcomingResponseDTO = movieApi.getUpcomingMovies(
                page = page
            )
            val movies = apiResponse.results
            val endOfPaginatedReached = movies.isEmpty()

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    movieDatabase.upcomingMoviesRemoteKeyDao.clearAllRemoteKeys()
                    movieDatabase.upcomingDao.clearAll()
                }

                val prevKey = if (page > 1 )page-1 else null
                val nextKey = if (endOfPaginatedReached)null else page+1
                val remoteKeys = movies.map {
                    UpcomingMoviesRemoteKeysEntity(id = it.id, prevKey= prevKey, nextKey = nextKey, currentPage = page)
                }

                movieDatabase.upcomingMoviesRemoteKeyDao.insertKeys(remoteKeys)
                movieDatabase.upcomingDao.upsertAllMovies(movies.onEach { result -> result.page = page }.map { it.toUpcomingMovieEntity() })
            }

            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginatedReached
            )

        }catch (e: HttpException){
            return MediatorResult.Error(e)
        }catch (e : IOException){
            return MediatorResult.Error(e)
        }catch (e : Exception){
            return MediatorResult.Error(e)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UpcomingMoviesEntity>): UpcomingMoviesRemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { upcomingMovieEntity ->
            movieDatabase.upcomingMoviesRemoteKeyDao.getRemoteKeysByMovieId(upcomingMovieEntity.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UpcomingMoviesEntity>): UpcomingMoviesRemoteKeysEntity? {
        return state.pages.firstOrNull{
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { upcomingMovieEntity ->
            movieDatabase.upcomingMoviesRemoteKeyDao.getRemoteKeysByMovieId(upcomingMovieEntity.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UpcomingMoviesEntity>): UpcomingMoviesRemoteKeysEntity? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieDatabase.upcomingMoviesRemoteKeyDao.getRemoteKeysByMovieId(id)
            }
        }
    }
}