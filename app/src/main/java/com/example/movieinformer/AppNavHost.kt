package com.example.movieinformer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import com.example.movieinformer.domain.model.Movie
import com.example.movieinformer.domain.model.UpcomingMovie
import com.example.movieinformer.presentation.BottomNavItem
import com.example.movieinformer.presentation.popularMovies.MovieScreen
import com.example.movieinformer.presentation.movieDetail.MovieDetailScreen
import com.example.movieinformer.presentation.searchBar.SearchScreen
import com.example.movieinformer.presentation.upcomingMovies.UpcomingMovieScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier,
               navController: NavHostController,
               upcomingMovies : LazyPagingItems<UpcomingMovie>,
               movie: LazyPagingItems<Movie>) {
    NavHost(modifier = modifier, navController = navController, startDestination = BottomNavItem.Home.route ){
        composable(BottomNavItem.Home.route,){
            MovieScreen(movie = movie,navController )
        }
       composable(BottomNavItem.Upcoming.route){
           UpcomingMovieScreen(upcomingMovies, navController)
       }

        composable(BottomNavItem.Searching.route){
            SearchScreen()
        }
        composable(route = "detail/{movieId}/{flag}",
            arguments =listOf(
                navArgument("movieId"){
                    type = NavType.IntType},
                navArgument("flag"){
                    type = NavType.BoolType
                }
            )
        ){
            MovieDetailScreen(){
            }
        }
    }

}