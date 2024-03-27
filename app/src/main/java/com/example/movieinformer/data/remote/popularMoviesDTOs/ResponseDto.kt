package com.example.movieinformer.data.remote.popularMoviesDTOs

data class ResponseDto(
    val page: Int,
    val results: List<ResultDto>,
    val total_pages: Int,
    val total_results: Int
)