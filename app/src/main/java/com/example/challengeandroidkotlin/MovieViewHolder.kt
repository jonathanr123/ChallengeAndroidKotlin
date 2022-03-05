package com.example.challengeandroidkotlin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroidkotlin.databinding.CardMovieBinding
import com.squareup.picasso.Picasso

class MovieViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = CardMovieBinding.bind(view)
    fun bind(image: String, title: String){
        Picasso.get().load(image).into(binding.imageMovie)
        binding.titleMovie.text = title
    }
}