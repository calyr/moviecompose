package com.calyr.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.calyr.movieapp.screen.CinemaMap
import com.calyr.movieapp.screen.MovieDetailScreen
import com.calyr.movieapp.screen.MoviesScreen
import com.calyr.movieapp.screen.PromotionScreen
import com.calyr.movieapp.viewmodel.MovieViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    var uuid = UUID.randomUUID().toString()
    // Write a message to the database
    val database = Firebase.database
    val myRef = database.getReference("app_promotion")
    var titlePromotion by remember { mutableStateOf("")  }
    var text = "(${uuid}) Hello, World! This is the best promotion"
    myRef.setValue(text)

    myRef.addValueEventListener( object: ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDataChange(p0: DataSnapshot) {
            val value = p0.getValue(String::class.java)
            titlePromotion = value.toString()
        }
    })

    NavHost(
        navController = navController,
        startDestination = Screens.PromotionScreen.route// Screens.MoviesScreen.route
    ) {
        composable(Screens.MoviesScreen.route) {
            val movieViewModel : MovieViewModel = hiltViewModel()

            MoviesScreen(
                onClick = {
                   movieId -> navController.navigate("${Screens.MovieDetailScreen.route}/${movieId}")
                },
                movieViewModel
            )
        }
        composable(
            route = "${Screens.MovieDetailScreen.route}/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                movieId = it.arguments?.getString("movieId")?:""
            )
        }
        composable(Screens.CinemaMapScreen.route) {
            CinemaMap()
        }

        composable(Screens.PromotionScreen.route) {
            PromotionScreen(title = titlePromotion)
        }
    }
}