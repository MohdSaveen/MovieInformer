package com.example.movieinformer.data.local.upcomingMovies.upcomingMoviesList

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieinformer.data.local.base.BaseEntityClass
import com.example.movieinformer.data.local.popularMovies.popularMoviesList.MoviesEntity

@Entity("upcomingMovies")
data class UpcomingMoviesEntity(

    @Embedded
    val baseEntityClass: BaseEntityClass,

    @PrimaryKey
val id: Int

    )