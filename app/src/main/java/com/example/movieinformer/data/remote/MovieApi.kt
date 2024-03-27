package com.example.movieinformer.data.remote

import com.example.movieinformer.data.remote.popularMoviesDTOs.ResponseDto
import com.example.movieinformer.data.remote.searchDTOs.SearchResponseDTO
import com.example.movieinformer.data.remote.upcomingMoviesDTOs.UpcomingResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page:Int,
        @Query("api_key") api:String= API_KEY
    ):ResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") api: String = API_KEY
    ) : UpcomingResponseDTO

    @GET("search/movie")
    suspend fun getSearchedList(
        @Query("query") query:String,
        @Query("page") page: Int,
        @Query("api_key") api: String = API_KEY
    ) : SearchResponseDTO

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1374bd60e524457bdf240ab9aa5f044d"
//        const val SEARCH_BASE_URL = "https://api.themoviedb.org/3/search/"
    }
}