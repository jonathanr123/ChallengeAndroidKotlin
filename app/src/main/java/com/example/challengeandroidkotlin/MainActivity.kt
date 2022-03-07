package com.example.challengeandroidkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroidkotlin.Adapter.MovieAdapter
import com.example.challengeandroidkotlin.Adapter.MovieAdapter.OnMovieClickListener
import com.example.challengeandroidkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private val movieImages = mutableListOf<ResultResponse>()
    lateinit var layoutManager: LinearLayoutManager

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        listPopularMovies()

        layoutManager= LinearLayoutManager(this)
        binding.rvMovie.layoutManager=layoutManager

        //API is called when the scroll reaches the end, to display new content
        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0){
                    val visibleItemCount= layoutManager.childCount
                    val pastVisibleItem= layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total= adapter.itemCount

                    println("$visibleItemCount - $pastVisibleItem - $total")
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        page++
                        listPopularMovies()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun initRecyclerView(){
        adapter = MovieAdapter(movieImages, this)
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.adapter = adapter
    }

    //Using retrofit to consume the api
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Call the api, save the response and display it in RecyclerView
    private fun listPopularMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getMoviePopular("popular?api_key=a9548caf23fe23d2ebb393ca325cd3bf&language=en-US&page=$page")
            val moviesData = call.body()?.results

            runOnUiThread {
                if(call.isSuccessful){
                    movieImages.addAll(moviesData!!)

                    if (::adapter.isInitialized) {
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter = MovieAdapter(movieImages,this@MainActivity)
                        binding.rvMovie.adapter = adapter
                    }

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

    //Pass the ID of the selected movie to MovieDetailActivity
    override fun onMovieClick(id: Int) {
        val intent = Intent (this, MovieDetailActivity::class.java)
        intent.putExtra("id","$id")
        startActivity(intent)
    }

}

