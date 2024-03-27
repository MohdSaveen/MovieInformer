package com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingMoviesDao {

    @Upsert
    suspend fun upsertAllMovies(movies:List<UpcomingMoviesEntity>)

    @Query("select * from upcomingMovies order by timing ")
    fun remotePagingSource(): PagingSource<Int, UpcomingMoviesEntity>

    @Query("select * from upcomingMovies where id =:id")
    fun getMovieById(id: Int): Flow<UpcomingMoviesEntity>

    @Query("Delete from upcomingMovies")
    suspend fun clearAll()

}