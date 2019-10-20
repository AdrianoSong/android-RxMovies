package com.br.song.example.skymoviesrx.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.song.example.skymoviesrx.adapter.MovieAdapter
import com.br.song.example.skymoviesrx.api.SkyMovies
import com.br.song.example.skymoviesrx.model.Movie

class MainViewModel : ViewModel() {
    private val moviesApi = SkyMovies()
    var movies = MutableLiveData<List<Movie>>()

    fun callAllMovies(adapter: MovieAdapter) {
        moviesApi.getMovies(adapter)
    }

    fun fetchLiveDateMovies() {
        moviesApi.getMoviesToLiveData(this)
    }

    fun getLiveDataMovies(): LiveData<List<Movie>> {
        return movies
    }
}
