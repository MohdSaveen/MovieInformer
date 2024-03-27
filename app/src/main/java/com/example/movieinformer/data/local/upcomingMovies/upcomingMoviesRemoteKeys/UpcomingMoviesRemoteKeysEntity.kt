package com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesRemoteKeys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UpcomingMoviesRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val prevKey : Int?,
    val nextKey : Int?,
    val currentPage : Int,
    @ColumnInfo("created_at")
    val createdAt : Long = System.currentTimeMillis()
)