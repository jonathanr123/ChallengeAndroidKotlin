package com.example.challengeandroidkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import com.example.challengeandroidkotlin.databinding.ActivityMovieDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetailActivity : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    private lateinit var binding: ActivityMovieDetailBinding
    private var idSelect= String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idSelect= intent.getStringExtra("id")!!
        showMovieDetail(idSelect)

        binding.rateBar.onRatingBarChangeListener = this
    }

    //Using retrofit to consume the api
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Call the api, save the response and display it in view Movie Detail
    @SuppressLint("SetTextI18n")
    private fun showMovieDetail(idSelect: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getMovieDetail("$idSelect?api_key=a9548caf23fe23d2ebb393ca325cd3bf&language=en-US")
            val movieDetail = call.body()

            runOnUiThread {
                if(call.isSuccessful){
                    Picasso.get().load("https://image.tmdb.org/t/p/w500_and_h282_face"+movieDetail?.poster_path).into(binding.imageMovieDetail)
                    binding.titleMovieDetail.text= movieDetail?.title
                    binding.descriptionMovieDetail.text= "Description: "+movieDetail?.description
                    binding.popularityMovie.text="Popularity: "+movieDetail?.popularity
                    binding.dateMovieDetail.text= "Release Date: "+movieDetail?.date
                    binding.languageMovieDetail.text= "Language: "+movieDetail?.language
                    var genreDetail =""
                    for (genre in movieDetail?.genres!!) {
                        genreDetail += if (genre == movieDetail.genres.last()){
                            "${genre.name}."
                        }else{
                            "${genre.name}, "
                        }
                    }
                    binding.genreMovieDetail.text= "Genre: $genreDetail"

                }else{
                    showError()
                }
            }

        }
    }

    //Update a movie's rating by making an API call
    private fun updateMovieRating(p1:Float){
        val data= Rating(p1)
        val id=idSelect
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java)
                .updateRating("$id/rating?api_key=a9548caf23fe23d2ebb393ca325cd3bf&guest_session_id=22ac4c3cb0ebf74fd893188206f8d85b",data)

            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(this@MovieDetailActivity, "Rating updated successfully!", Toast.LENGTH_SHORT).show()

                } else {
                    showError()
                }
            }
        }
    }

    //Show an error message
    private fun showError() {
        Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show()
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        updateMovieRating(p1)
    }

}