package com.example.movieinformer.presentation.movieDetail

data class MovieDetailsState(
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val original_language: String ="",
    val original_title: String = "",
    val overview: String = "" ,
    val release_date: String = "",
    val title: String = "",
    val vote_average: Double = 0.0 ,
    val vote_count: Int  = 0
)