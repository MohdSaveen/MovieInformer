package com.example.movieinformer.data.mappers

import com.example.movieinformer.data.local.base.BaseEntityClass
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity
import com.example.movieinformer.data.remote.popularMoviesDTOs.ResultDto
import com.example.movieinformer.domain.model.BaseModelClass
import com.example.movieinformer.domain.model.Movie
import com.example.movieinformer.domain.model.MovieDetails


fun ResultDto.toMovieEntity() : MoviesEntity {
    return MoviesEntity(
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

fun MoviesEntity.toMovie() : Movie {
    return Movie(
        BaseModelClass(
        adult = baseEntity.adult,
//        backdrop_path = backdrop_path,
//        genre_ids = genre_ids,
        id = id,
//        original_language = original_language,
        original_title = baseEntity.original_title,
        overview = baseEntity.overview,
//        popularity = popularity,
        poster_path = baseEntity.poster_path,
        release_date = baseEntity.release_date,
        title = baseEntity.title,
//        video = video,
        vote_average = baseEntity.vote_average,
//        vote_count = vote_count
    )
    )
}

fun MoviesEntity.toMovieDetails() : MovieDetails{
    return MovieDetails(
        adult = baseEntity.adult,
        backdrop_path = baseEntity.backdrop_path,
        genre_ids = baseEntity.genre_ids,
        id = id,
        original_language = baseEntity.original_language,
        original_title = baseEntity.original_title,
        overview = baseEntity.overview,
        popularity = baseEntity.popularity,
        poster_path = baseEntity.poster_path,
        release_date = baseEntity.release_date,
        title = baseEntity.title,
        video = baseEntity.video,
        vote_average = baseEntity.vote_average,
        vote_count = baseEntity.vote_count,
    )
}