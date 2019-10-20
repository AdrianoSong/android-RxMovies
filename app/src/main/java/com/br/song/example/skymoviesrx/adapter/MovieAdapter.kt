package com.br.song.example.skymoviesrx.adapter

import android.content.Context
import com.br.song.example.skymoviesrx.model.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.br.song.example.skymoviesrx.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    private val myDataSet: MutableList<Movie>,
    private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageUrl = myDataSet[position].cover_url

        holder.txtMovieName.text = myDataSet[position].title
        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions().override(100, 100))
            .into(holder.imgMovieCover)

        holder.txtMovieName.setOnClickListener {
            val bundle = bundleOf("index" to position)

            it.findNavController().navigate(R.id.toMovieDetails, bundle)
        }

        holder.imgMovieCover.setOnClickListener {
            val bundle = bundleOf("index" to position)

            it.findNavController().navigate(R.id.toMovieDetails, bundle)
        }
    }

    fun update(newData: MutableList<Movie>) {
        myDataSet.clear()
        myDataSet.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val txtMovieName: TextView = itemView.textViewMovieName
        val imgMovieCover: ImageView = itemView.imageViewMovieCover
    }
}