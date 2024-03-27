package com.example.movieinformer.data.local.searchMovies.searchMoviesRemoteKeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKeys: List<SearchRemoteKeys>)

    @Query("select * from SearchRemoteKeys where id = :id")
    suspend fun getRemoteKeysByMovieId(id : Int) : SearchRemoteKeys?

    @Query("delete from SearchRemoteKeys")
    suspend fun clearAllRemoteKeys()

    @Query("select created_at from SearchRemoteKeys Order by created_at DESC Limit 1")
    suspend fun getCreationTime():Long?

}