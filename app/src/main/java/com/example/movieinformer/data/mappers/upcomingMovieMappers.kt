package com.example.movieinformer.data.mappers

import com.example.movieinformer.data.local.base.BaseEntityClass
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList.UpcomingMoviesEntity
import com.example.movieinformer.data.remote.popularMoviesDTOs.ResultDto
import com.example.movieinformer.data.remote.upcomingMoviesDTOs.Result
import com.example.movieinformer.domain.model.BaseModelClass
import com.example.movieinformer.domain.model.Movie
import com.example.movieinformer.domain.model.MovieDetails
import com.example.movieinformer.domain.model.UpcomingMovie

fun Result.toUpcomingMovieEntity() : UpcomingMoviesEntity {
    return UpcomingMoviesEntity(
        BaseEntityClass(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
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
        ),
        id = id
    )
}

fun UpcomingMoviesEntity.toUpcomingMovie() : UpcomingMovie {
    return UpcomingMovie(
        BaseModelClass(
        adult = baseEntityClass.adult,
//        backdrop_path = backdrop_path,
//        genre_ids = genre_ids,
        id = id,
//        original_language = original_language,
        original_title = baseEntityClass.original_title,
        overview = baseEntityClass.overview,
//        popularity = popularity,
        poster_path = baseEntityClass.poster_path,
        release_date = baseEntityClass.release_date,
        title = baseEntityClass.title,
//        video = video,
        vote_average = baseEntityClass.vote_average,
//        vote_count = vote_count
    )
    )
}

fun UpcomingMoviesEntity.toMovieDetails() : MovieDetails {
    return MovieDetails(
        adult = baseEntityClass.adult,
        backdrop_path = baseEntityClass.backdrop_path,
        genre_ids = baseEntityClass.genre_ids,
        id = id,
        original_language = baseEntityClass.original_language,
        original_title = baseEntityClass.original_title,
        overview = baseEntityClass.overview,
        popularity = baseEntityClass.popularity,
        poster_path = baseEntityClass.poster_path,
        release_date = baseEntityClass.release_date,
        title = baseEntityClass.title,
        video = baseEntityClass.video,
        vote_average = baseEntityClass.vote_average,
        vote_count = baseEntityClass.vote_count,
    )
}