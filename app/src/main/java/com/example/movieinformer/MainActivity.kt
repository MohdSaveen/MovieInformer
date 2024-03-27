package com.example.movieinformer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinformer.presentation.BottomNavItem
import com.example.movieinformer.presentation.popularMovies.MovieScreen
import com.example.movieinformer.presentation.popularMovies.MovieViewModel
import com.example.movieinformer.presentation.searchBar.SearchViewModel
import com.example.movieinformer.presentation.upcomingMovies.UpcomingMoviesViewModel
import com.example.movieinformer.ui.theme.MovieInformerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieInformerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //popular movies ViewModel
                    val viewModel: MovieViewModel = hiltViewModel()
                    val movies = viewModel.moviePagingFlow.collectAsLazyPagingItems()

                        //upcoming movies ViewModel

                    val upcomingViewmodel : UpcomingMoviesViewModel = hiltViewModel()
                    val upcomingMovies = upcomingViewmodel.moviePagingFlow.collectAsLazyPagingItems()

                    val navController = rememberNavController()
                    var selectedItem by remember{ mutableStateOf(BottomNavItem.Home) }

                    var selected by remember{
                        mutableIntStateOf(0)
                    }
                    val searchedViewmodel : SearchViewModel = hiltViewModel()




                Scaffold(bottomBar = {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.onSecondaryContainer){
                        val navItems= listOf(
                            BottomNavItem.Home,
                            BottomNavItem.Upcoming,
                            BottomNavItem.Searching
                        )

                        val currentRoute = navController.currentDestination?.route

                            navItems.forEachIndexed {index, screen ->
                               NavigationBarItem(colors = NavigationBarItemDefaults.colors(
                                   selectedTextColor = Color.White,
                                   disabledTextColor = Color.Gray
                               ),
                                   selected = index ==selected,
                                   onClick = {
                                       selected = index

                                       navController.navigate(screen.route) {
                                           popUpTo(navController.graph.startDestinationId) {
                                               saveState = true
                                           }
                                           launchSingleTop = true
                                           restoreState = false
                                       }

                                             },
                                   icon = { Icon(imageVector =
                                   if (index == selected) {
                                       screen.selectedIcon
                                   }else{screen.unselectedIcon},
                                       contentDescription = screen.label) },
                                   label = { Text(text = screen.label)}, )
                            }
                    }
                }
                ) {

                    AppNavHost(navController = navController, movie = movies, upcomingMovies = upcomingMovies, modifier = Modifier.padding(it))

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()



    }
}

