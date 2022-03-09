package com.example.challengeandroidkotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isInvisible
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

class MainActivity : AppCompatActivity(), OnMovieClickListener, OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private var movieImages = mutableListOf<ResultResponse>()
    private var movieCopy = mutableListOf<ResultResponse>()
    lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var control=1

    private var scrollEnabled=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        listPopularMovies("")

        binding.searchMovie.setOnQueryTextListener(this)

        layoutManager = LinearLayoutManager(this)
        binding.rvMovie.layoutManager = layoutManager

        //API is called when the scroll reaches the end, to display new content
        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount
                    if (scrollEnabled){
                        println("$visibleItemCount - $pastVisibleItem - $total")
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            page++
                            listPopularMovies("")
                        }
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
    @SuppressLint("NotifyDataSetChanged")
    private fun listPopularMovies(newText: String?){
        if (newText==""||newText==null) {
            binding.messageEmpty.visibility= View.GONE
            scrollEnabled=true

            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(APIService::class.java)
                    .getMoviePopular("popular?api_key=a9548caf23fe23d2ebb393ca325cd3bf&language=en-US&page=$page")
                val moviesData = call.body()?.results

                runOnUiThread {
                    if (call.isSuccessful) {
                        if (page==1){
                            movieImages.clear()
                            movieCopy.clear()
                        }
                        movieImages.addAll(moviesData!!)
                        if (control==1){
                        movieCopy=movieImages
                        control++}
                        println(movieCopy)

                        if (::adapter.isInitialized) {
                            adapter.notifyDataSetChanged()
                        } else {
                            adapter = MovieAdapter(movieImages, this@MainActivity)
                            binding.rvMovie.adapter = adapter
                        }

                    } else {
                        showError()
                    }
                }
            }
        }
        else{
            scrollEnabled=false
            val movieFilter= movieCopy.filter { element -> element.title.lowercase().contains(newText)} as MutableList<ResultResponse>
            if(movieFilter.count()==0 && newText!=""){
                binding.messageEmpty.visibility=View.VISIBLE
                binding.messageEmpty.text="No results found"
            }else{
                binding.messageEmpty.visibility= View.GONE
            }
            movieCopy.addAll(movieImages)
            movieImages.clear()
            movieImages.addAll(movieFilter)
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()

                page=1
            } else {
                adapter = MovieAdapter(movieImages,this@MainActivity)
                binding.rvMovie.adapter = adapter
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        listPopularMovies(newText?.lowercase())
        return true
    }

}

