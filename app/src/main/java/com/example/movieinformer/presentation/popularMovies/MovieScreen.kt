package com.example.movieinformer.presentation.popularMovies

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.movieinformer.domain.model.Movie
import com.example.movieinformer.presentation.popularMovies.PopularScreenItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    movie: LazyPagingItems<Movie>,
    navController: NavController
) {

    val loadState = movie.loadState.mediator
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val context = LocalContext.current
    LaunchedEffect(key1 = loadState){
        if (loadState?.refresh is LoadState.Error){
            Toast.makeText(
                context,
                "Error: " + (movie.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSecondary),
                title = {
                    Text(text = "Popular Movies",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }, scrollBehavior = scrollBehavior,
                )
        }) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (loadState?.refresh == LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(200.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(movie.itemCount) { index ->
                        movie[index]?.let {
                            PopularScreenItem(movie = it, onClick = { navController.navigate("detail/${it.id}/${true}") })
                        }

                    }
                    item {
                        if (loadState?.append == LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }


}