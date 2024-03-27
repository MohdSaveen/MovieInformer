package com.example.movieinformer.data.mappers

import com.example.movieinformer.data.local.searchMovies.searchMoviesList.SearchMovieEntity
import com.example.movieinformer.data.remote.searchDTOs.Result
import com.example.movieinformer.domain.model.SearchMovie


fun Result.toSearchMoviesEntity(): SearchMovieEntity {
    return SearchMovieEntity(
//        adult = adult,
//        backdrop_path = backdrop_path,
//        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
//        original_title = original_title,
//        overview = overview,
//        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
//        video = video,
        vote_average = vote_average,
//        vote_count = vote_count,
        page = page
    )
}

fun SearchMovieEntity.toSearchMovie() : SearchMovie{
    return SearchMovie(
//        adult = adult,
//        backdrop_path = backdrop_path,
//        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
//        original_title = original_title,
//        overview = overview,
//        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
//        video = video,
        vote_average = vote_average,
//        vote_count = vote_count
    )
}






























