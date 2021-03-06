package com.example.challengeandroidkotlin

import com.google.gson.annotations.SerializedName

//data class used in MainActivity
data class ResultResponse(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("backdrop_path") var poster_path: String
)
data class MovieResponse (
    @SerializedName("page") var page: Int,
    @SerializedName("total_results") var total_results: Int,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("results") var results: List<ResultResponse>
)

//data class used in MovieDetailActivity
data class GenreResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
)
data class MovieDetailResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("poster_path") var poster_path: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("overview") var description: String,
    @SerializedName("release_date") var date: String,
    @SerializedName("original_language") var language:String,
    @SerializedName("genres") var genres: List<GenreResponse>
)

data class Rating(
    var value:Float
)

data class Message(
    var success: Boolean,
    var status_code: Int,
    var status_message: String
)
