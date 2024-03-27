package com.example.movieinformer.domain.model

import androidx.room.PrimaryKey

data class Movie(
    val adult: Boolean,
//    val backdrop_path: String? = "",
//    val genre_ids: List<Int> = emptyList(),
    val id: Int,
//    val original_language: String,
    val original_title: String,
    val overview: String = "",
//    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
//    val video: Boolean= false,
    val vote_average: Double = 0.0,
//    val vote_count: Int = 0
)