package com.example.challengeandroidkotlin

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getMoviePopular(@Url url:String): Response<MovieResponse>
}