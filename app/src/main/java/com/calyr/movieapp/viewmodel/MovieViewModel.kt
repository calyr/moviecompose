package com.calyr.movieapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyr.data.MovieRepository
import com.calyr.domain.Movie
import com.calyr.movieapp.util.hasInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val context: Context
) : ViewModel() {
    sealed class MovieState {
        object Loading : MovieState()
        class Error( val errorMessage: String? = null): MovieState()
        class Successful(val list: List<Movie> = emptyList()): MovieState()
    }

    private val _state = MutableStateFlow<MovieState>(MovieState.Loading)
    val state: StateFlow<MovieState> = _state

    init {
        fetchData()
    }
    fun fetchData() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = movieRepository.obtainMovies(hasInternet = hasInternet(context) )
                withContext(Dispatchers.Main) {
                    _state.value = MovieState.Successful(list = movies)
                }
            } catch (e: Exception) {
                // Handle error state
                Log.e("MOVIE", "Error fetching movies", e)
                withContext(Dispatchers.Main) {
                    _state.value = MovieState.Error( errorMessage = e.message)
                }
            }
        }
    }
}

