package com.example.movieinformer.data.mappers

import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.data.remote.popularMoviesDTOs.ResultDto
import com.example.movieinformer.data.remote.upcomingMoviesDTOs.Result
import com.example.movieinformer.domain.model.Movie
import com.example.movieinformer.domain.model.MovieDetails
import com.example.movieinformer.domain.model.UpcomingMovie

fun Result.toUpcomingMovieEntity() : UpcomingMoviesEntity {
    return UpcomingMoviesEntity(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        timing = page
    )
}

fun UpcomingMoviesEntity.toUpcomingMovie() : UpcomingMovie {
    return UpcomingMovie(
        adult = adult,
//        backdrop_path = backdrop_path,
//        genre_ids = genre_ids,
        id = id,
//        original_language = original_language,
        original_title = original_title,
        overview = overview,
//        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
//        video = video,
        vote_average = vote_average,
//        vote_count = vote_count
    )
}

fun UpcomingMoviesEntity.toMovieDetails() : MovieDetails {
    return MovieDetails(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}