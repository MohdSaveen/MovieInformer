package com.example.movieinformer.data.local.popularMovies.popularMoviesList

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAllMovies(movies:List<MoviesEntity>)

    @Query("select * from movieEntity order by timing ")
    fun pagingSource():PagingSource<Int, MoviesEntity>

    @Query("select * from movieEntity where id =:id")
    fun getMovieById(id: Int): Flow<MoviesEntity>

    @Query("Delete from movieEntity")
    suspend fun clearAll()

}