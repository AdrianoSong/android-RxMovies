package com.br.song.example.skymoviesrx.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.song.example.skymoviesrx.api.SkyMovies
import com.br.song.example.skymoviesrx.model.Movie

class MainViewModel : ViewModel() {
    private val moviesApi = SkyMovies()
    private var movies = MutableLiveData<List<Movie>>()

    init {
        fetchLiveDateMovies()
    }

    fun getEmptyItemExample(): Movie {
        return Movie(
            "No title",
            "nothing",
            "no",
            "no",
            "http//google.com",
            listOf<String>(),
            "nada"
        )
    }

    fun setMovies(movieList: List<Movie>) {
        movies.value = movieList
    }

    private fun fetchLiveDateMovies() {
        moviesApi.getMoviesToLiveData(this)
    }

    fun getLiveDataMovies(): LiveData<List<Movie>> {
        return movies
    }
}
