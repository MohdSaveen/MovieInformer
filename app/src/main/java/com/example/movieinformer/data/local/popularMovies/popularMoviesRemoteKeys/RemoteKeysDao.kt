package com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKeys: List<RemoteKeys>)

    @Query("select * from RemoteKeys where id = :id")
    suspend fun getRemoteKeysByMovieId(id : Int) : RemoteKeys?

    @Query("delete from RemoteKeys")
    suspend fun clearAllRemoteKeys()

    @Query("select created_at from RemoteKeys Order by created_at DESC Limit 1")
    suspend fun getCreationTime():Long?

}