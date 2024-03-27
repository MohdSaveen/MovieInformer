package com.example.movieinformer.data.local.popularMovies.popularMoviesList

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieinformer.data.local.base.BaseEntityClass

@Entity("movieEntity")
data class MoviesEntity(

    @Embedded
    val baseEntity: BaseEntityClass,

    @PrimaryKey
val id: Int
)
