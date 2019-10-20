package com.br.song.example.skymoviesrx.api

import android.util.Log
import com.br.song.example.skymoviesrx.adapter.MovieAdapter
import com.br.song.example.skymoviesrx.model.Movie
import com.br.song.example.skymoviesrx.ui.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface SkyMoviesInterface {
    @GET("Movies")
    fun moviesList() : Observable<List<Movie>>
}

class SkyMovies {

    private var service: SkyMoviesInterface? = null

    init {
        val retrofit = setupRetrofit()

        service = retrofit.create(SkyMoviesInterface::class.java)
    }

    private fun setupRetrofit(): Retrofit {
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(httpLogging)

        //full API path
        //"https://sky-exercise.herokuapp.com/api/Movies"
        return Retrofit.Builder()
            .baseUrl("https://sky-exercise.herokuapp.com/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    fun getMovies(adapter: MovieAdapter) =
        service?.let {
            it.moviesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { movieList -> adapter.update(movieList.toMutableList()) },
                { error -> Log.e("MoviessApi", "onError: ${error.stackTrace}") },
                {Log.e("MoviessApi", "OnComplete()")}
            )
        }

    fun getMoviesToLiveData(viewModel: MainViewModel) =
        service?.let {
            it.moviesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    { movieList -> viewModel.movies.value = movieList },
                    { error -> Log.e("MoviessApi", "onError: ${error.stackTrace}") },
                    {Log.e("MoviessApi", "OnComplete()")}
                )
        }
}