package com.br.song.example.skymoviesrx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.br.song.example.skymoviesrx.ui.main.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_details.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var movieIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        defineArgumentsFromNavigation()

        setupViewModel()

        setupAllViewData()
    }

    private fun defineArgumentsFromNavigation() {
        movieIndex = arguments?.getInt("index")
    }

    private fun setupViewModel() {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun setupAllViewData() {
        viewModel.getLiveDataMovies().observe(this, Observer { allMovies ->
            movieIndex?.let {
                textViewTitle.text = allMovies[it].title
                textViewOverview.text = allMovies[it].overview
                Glide.with(context)
                    .load(allMovies[it].cover_url)
                    .into(imageViewCover)
            }
        })
    }
}
