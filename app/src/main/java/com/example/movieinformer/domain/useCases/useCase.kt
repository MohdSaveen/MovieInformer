package com.example.movieinformer.domain.useCases



data class useCase(
    val getMovieById: GetMovieById,
    val upcomingMovieById: GetUpcomingMovieById
)