package com.example.movieinformer.data.local.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<T>)

    @Query("Delete from ${BaseDao.TABLE_NAME}")
    suspend fun clearAll()


    companion object {
        const val TABLE_NAME = "table_name"
    }
}