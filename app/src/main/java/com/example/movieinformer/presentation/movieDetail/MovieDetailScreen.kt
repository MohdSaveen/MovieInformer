package com.example.movieinformer.presentation.movieDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.movieinformer.R
import com.example.movieinformer.domain.utils.MapGenre


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MovieDetailScreen(viewModel: MovieDetailsViewModel = hiltViewModel(),
                      onNavigation: ()-> Unit) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val painter =
        rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original" + state.backdrop_path)

    Column(modifier = Modifier
        .background(Color.Black)
        .fillMaxWidth()
        .fillMaxSize()
    ) {

        Image(painter = painter,contentDescription = "background Path", modifier = Modifier.padding(5.dp))

        Column(modifier = Modifier
            .verticalScroll(scrollState, enabled = true)
            .padding(10.dp)) {

            CardForDetails("Title :",state = state.original_title, fontSize = 24.sp)
            CardForDetails("release Date :",state.release_date, 24.sp)
            CardForDetails("Overview :",state = state.overview, fontSize = 24.sp)
            CardForDetails(titleText = "Original Language :", state = state.original_language, fontSize = 24.sp)
            ElevatedCard(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.elevatedCardColors(
                    MaterialTheme.colorScheme.onSecondaryContainer
                )) {
                Text(text = "Genre",
                    fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(8.dp))
                Divider(thickness = 1.dp, color = Color.White, modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth())
                FlowRow(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3
                ) {
                    val itemModifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                    repeat(state.genre_ids.size){item ->
                        MapGenre.mappingPossible[state.genre_ids[item]]
                            ?.let { genre -> Text(text = "$genre ",
                                modifier = Modifier.padding(8.dp), color = Color.White, fontSize = 20.sp) }
                        
                        
                    }

                }
            }

        }


    }
    
}

@Composable
fun CardForDetails(titleText: String,state: Any, fontSize: TextUnit) {
    ElevatedCard(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.elevatedCardColors(
            MaterialTheme.colorScheme.onSecondaryContainer)) {
        titleDivider(titleText = titleText, state =state , fontSize = fontSize)

    }
}

@Composable
fun titleDivider(titleText : String,state: Any,fontSize: TextUnit) {
    Row {
        Text(text = titleText,
            fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(8.dp))
        Text(text = "$state",
            fontSize = fontSize, color = Color.White, modifier = Modifier.padding(8.dp), fontStyle = FontStyle.Italic)
    }


}
