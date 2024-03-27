package com.example.movieinformer.data.remote.searchDTOs

data class SearchResponseDTO(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)