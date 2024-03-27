package com.example.movieinformer.data.remote.upcomingMoviesDTOs

data class UpcomingResponseDTO(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)