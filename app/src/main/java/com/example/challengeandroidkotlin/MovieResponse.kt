package com.example.challengeandroidkotlin

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("poster_path") var poster_path: String
)
data class MovieResponse (
    @SerializedName("page") var page: Int,
    @SerializedName("total_results") var total_results: Int,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("results") var results: List<ResultResponse>
        )