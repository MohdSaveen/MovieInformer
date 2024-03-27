package com.example.movieinformer.domain.utils

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromList(list: List<Int>) : String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String) : List<Int>{
        return data.split(",").map { it.toInt() }
    }
}