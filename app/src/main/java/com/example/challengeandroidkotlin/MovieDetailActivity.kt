package com.example.challengeandroidkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.challengeandroidkotlin.databinding.ActivityMovieDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idSelect= intent.getStringExtra("id")
        showMovieDetail(idSelect)
    }

    //Using retrofit to consume the api
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Call the api, save the response and display it in view Movie Detail
    private fun showMovieDetail(idSelect: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getMovieDetail(idSelect+"?api_key=a9548caf23fe23d2ebb393ca325cd3bf&language=en-US")
            val movieDetail = call.body()

            runOnUiThread {
                if(call.isSuccessful){
                    Picasso.get().load("https://image.tmdb.org/t/p/w500_and_h282_face"+movieDetail?.poster_path).into(binding.imageMovieDetail)
                    binding.titleMovieDetail.text= movieDetail?.title
                    binding.descriptionMovieDetail.text= "Description: "+movieDetail?.description
                    binding.dateMovieDetail.text= "Release Date: "+movieDetail?.date
                    binding.languageMovieDetail.text= "Language: "+movieDetail?.language
                    var genreDetail: String=""
                    for (genre in movieDetail?.genres!!) {
                        if (genre == movieDetail.genres.last()){
                            genreDetail += "${genre.name}."
                        }else{
                            genreDetail += "${genre.name}, "
                        }
                    }
                    binding.genreMovieDetail.text= "Genre: "+genreDetail

                }else{
                    showError()
                }
            }

        }
    }

    //Show an error message
    private fun showError() {
        Toast.makeText(this, "An error has ocurred", Toast.LENGTH_SHORT).show()
    }

}