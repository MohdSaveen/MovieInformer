package com.example.movieinformer.data.local.popularMovies.popularMoviesList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movieEntity")
data class MoviesEntity(
    val adult: Boolean,
    val backdrop_path: String?="",
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

    var timing:Int,

    @PrimaryKey val id: Int
)
