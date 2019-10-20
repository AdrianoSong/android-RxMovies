package com.br.song.example.skymoviesrx.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.br.song.example.skymoviesrx.R
import com.br.song.example.skymoviesrx.adapter.MovieAdapter
import com.br.song.example.skymoviesrx.model.Movie
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var movies = mutableListOf<Movie>()
    private var adapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
        setupRecyclerView()

//        fetchMovies() //without livedata
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //using liveData to fetch all data from movies
        viewModel.getLiveDataMovies().observe(this, Observer {
            adapter?.update(it.toMutableList())
        })

        viewModel.fetchLiveDateMovies()
    }

    private fun setupRecyclerView() {
        val context = context?.let { it } ?: return

        adapter = MovieAdapter(movies, context)

        recyclerviewMovies.setHasFixedSize(true)
        recyclerviewMovies.layoutManager = GridLayoutManager(context, 2)
        recyclerviewMovies.adapter = adapter
    }

    private fun fetchMovies() {
        val adapter = adapter?.let { it } ?: return

        viewModel.callAllMovies(adapter)
    }
}
