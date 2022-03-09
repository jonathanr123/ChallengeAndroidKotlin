package com.example.challengeandroidkotlin

import retrofit2.Response
import retrofit2.http.*

interface APIService {
    //function that gets a list of popular movies from the TMDB API
    @GET
    suspend fun getMoviePopular(@Url url:String): Response<MovieResponse>

    //function that gets details of a movie by id from the TMDB API
    @GET
    suspend fun getMovieDetail(@Url url:String): Response<MovieDetailResponse>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST
    suspend fun updateRating(@Url url: String, @Body data: Rating): Response<Message>
}