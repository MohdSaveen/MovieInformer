package com.example.movieinformer.presentation.searchBar

import com.example.movieinformer.domain.model.SearchMovie

data class SearchScreenState (
    var query : String = "",
    val hint : String = ""
)