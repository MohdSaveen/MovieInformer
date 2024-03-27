package com.example.movieinformer.presentation.searchBar

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {


    var pushSearch by remember{
        mutableStateOf(false)
    }
    var searchItem  by remember{
        mutableStateOf("")
    }
    val movie = viewModel.searchingMovie.collectAsLazyPagingItems()

    val onMyFocused = remember {
        mutableStateOf(false)
    }

    val loadState = movie?.loadState?.mediator

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        CenterAlignedTopAppBar(title = {
            if (pushSearch) {
                TextField(value = searchItem, onValueChange ={string->
                    searchItem = string
                }, keyboardActions = KeyboardActions(onSearch = {
                    viewModel.getSearchMovie(searchItem)
                }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), maxLines = 1)
            } else {
                Text(
                    text = "Search Movies",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            navigationIcon = {
                             if (pushSearch){
                                 IconButton(onClick = {
                                     pushSearch = false
                                 }) {
                                     Icon(imageVector = Icons.Filled.ArrowBack, contentDescription ="" )

                                 }
                             }
            },
            actions = {if (!pushSearch) {
                IconButton(
                    onClick = {
                        pushSearch = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = "Search Button"
                    )

                }
            }
            },
            scrollBehavior = scrollBehavior
        )
    }
    ) {


        Log.d("TAG", "SearchScreen: ${movie?.itemSnapshotList}, ${movie?.itemCount}")

        Box(
            modifier = Modifier
                .padding(it)
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
                    items(count = movie!!.itemCount) { index ->
                        movie[index]?.let { searchMovie ->
                            SearchScreenItem(movie = searchMovie, onClick = {})
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

@Composable
fun SearchingBtn() {
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSearch() {


}