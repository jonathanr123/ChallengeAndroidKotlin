package com.example.challengeandroidkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeandroidkotlin.Adapter.MovieAdapter
import com.example.challengeandroidkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private val movieImages = mutableListOf<ResultResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        listPopularMovies()

    }

    private fun initRecyclerView(){
        adapter = MovieAdapter(movieImages)
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun listPopularMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getMoviePopular("popular?api_key=a9548caf23fe23d2ebb393ca325cd3bf&language=en-US&page=1")
            val moviesData = call.body()?.results
            //println("${puppies?.results} DE UNA MAS") //Forma de entrar y manipular la info de array
            //val imagesMovie= arrayListOf<String>()
            /*val moviesData=puppies?.results
            for (movie in puppies?.results!!){
                imagesMovie.add("https://image.tmdb.org/t/p/w500_and_h282_face"+movie.poster_path)
            }*/
            println("$moviesData LISTA DE PELICULAS")

            runOnUiThread {
                if(call.isSuccessful){
                    movieImages.clear()
                    movieImages.addAll(moviesData!!)
                    adapter.notifyDataSetChanged()

                }else{
                    /*showError()*/
                }
            }
        }
    }
}

