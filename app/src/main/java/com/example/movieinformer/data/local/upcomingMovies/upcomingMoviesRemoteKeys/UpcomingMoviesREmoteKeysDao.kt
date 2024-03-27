package com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieinformer.data.local.popularMovies.popularMoviesRemoteKeys.RemoteKeys


@Dao
interface UpcomingMoviesREmoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKeys: List<UpcomingMoviesRemoteKeysEntity>)

    @Query("select * from UpcomingMoviesRemoteKeysEntity where id = :id")
    suspend fun getRemoteKeysByMovieId(id : Int) : UpcomingMoviesRemoteKeysEntity?

    @Query("delete from UpcomingMoviesRemoteKeysEntity")
    suspend fun clearAllRemoteKeys()

    @Query("select created_at from UpcomingMoviesRemoteKeysEntity Order by created_at DESC Limit 1")
    suspend fun getCreationTime():Long?
}