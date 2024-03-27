package com.example.movieinformer.data.local.searchMovies.searchMoviesList

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchMoviesDao {

    @Upsert
    suspend fun upsertAllMovies(movies:List<SearchMovieEntity>)

    @Query("SELECT * FROM SearchMovieEntity order by page")
    fun pagingSource(): PagingSource<Int, SearchMovieEntity>

    @Query("select * from SearchMovieEntity where id =:id")
    fun getMovieById(id: Int): Flow<SearchMovieEntity>

    @Query("Delete from SearchMovieEntity")
    suspend fun clearAll()

}