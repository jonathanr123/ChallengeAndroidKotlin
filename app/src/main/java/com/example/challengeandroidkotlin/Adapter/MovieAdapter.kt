package com.example.challengeandroidkotlin.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroidkotlin.MovieDetailActivity
import com.example.challengeandroidkotlin.MovieViewHolder
import com.example.challengeandroidkotlin.R
import com.example.challengeandroidkotlin.ResultResponse

class MovieAdapter(
    private val images: MutableList<ResultResponse>,
    private val itemClickListener: OnMovieClickListener
    ) : RecyclerView.Adapter<MovieViewHolder>() {

    interface OnMovieClickListener{
        fun onMovieClick(id:Int){
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.card_movie, parent, false))
    }
    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = images[position]
        println("${item.title} Esto es del adapter")
        holder.bind("https://image.tmdb.org/t/p/w500_and_h282_face"+item.poster_path, item.title)
        holder.itemView.setOnClickListener{ itemClickListener.onMovieClick(item.id)}
    }
}