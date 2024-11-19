package com.calyr.movieapp.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.calyr.domain.Movie
import com.calyr.movieapp.R
import com.calyr.movieapp.util.showNotification
import com.calyr.movieapp.viewmodel.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(onBackPressed: () -> Unit, movieId: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                }
            )
        },
        content = {
                paddingValues -> MovieDetailScreenContent( modifier = Modifier.padding(paddingValues),
                    movieId = movieId)
        }
    )
}

@Composable
fun MovieDetailScreenContent(modifier: Modifier, movieId: String) {
    Log.d("MovieDetailScreenContent", "MovieDetailScreenContent UI")
    val viewModel = MovieDetailViewModel()

    var movieUI by remember { mutableStateOf(Movie(1, "", "", "")) }

    viewModel.findMovie(LocalContext.current, movieId)
    val context = LocalContext.current

    fun updateUI(movieDetailState: MovieDetailViewModel.MovieDetailState) {
        when ( movieDetailState) {
            is MovieDetailViewModel.MovieDetailState.Error -> {
                Toast.makeText(context, "Error ${movieDetailState.message}", Toast.LENGTH_SHORT).show()
            }
            is MovieDetailViewModel.MovieDetailState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is MovieDetailViewModel.MovieDetailState.Successful -> {
                movieUI = movieDetailState.movie
            }
        }
    }
    viewModel.state.observe(
        LocalLifecycleOwner.current,
        Observer(::updateUI)
    )


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(
                LocalContext.current
            ).data( "${stringResource(R.string.poster_path)}${movieUI.posterPath}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Inside
        )
        Text(text = movieUI.title )
        Text(text = movieUI.description)
        IconButton(
            onClick = {
                showNotification(context, movieUI)
            }
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
            )
        }

    }
}