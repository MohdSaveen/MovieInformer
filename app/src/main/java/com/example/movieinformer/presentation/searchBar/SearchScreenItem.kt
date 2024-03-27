package com.example.movieinformer.presentation.searchBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieinformer.R
import com.example.movieinformer.domain.model.SearchMovie
import com.example.movieinformer.domain.model.UpcomingMovie

@Composable
fun SearchScreenItem(movie : SearchMovie, onClick : ()-> Unit) {
    val painter =
        rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original" + movie.poster_path)

    val percentageColor = remember {
        chooseColor(movie)
    }
    ElevatedCard(modifier = Modifier
        .padding(10.dp)
        .height(250.dp)
        .clickable { onClick() }
        .width(width = 175.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)
    ) {

        Box(modifier = Modifier.weight(5f)) {

            Column {

                Image(
                    painter = if (movie.poster_path.isNullOrEmpty()){
                        painterResource(id = R.drawable.error)}
                    else{ painter
                        },
                    contentDescription = "Poster Pic",
                    modifier = Modifier
                        .weight(5f)
                        .size(175.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

                Divider(thickness = 1.dp, color = Color.DarkGray)

                movie.release_date?.let {
                    Text(text = it,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }

                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(2f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Card(shape = CircleShape, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 55.dp)) {

                val percentage = (movie.vote_average?.div(100) ?: 0.0 ) *1000

                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(progress = movie.vote_average?.toFloat()?.div(10) ?: 1f, modifier = Modifier
                        .size(45.dp)
                        .background(
                            Color.Black
                        ), color = percentageColor,
                    )
                    Text(text = "${percentage.toInt()}%", fontWeight = FontWeight.Bold, color = Color.White)
                }

            }

        }
    }
}

fun chooseColor(movie: SearchMovie): Color {

    if (movie.vote_average!! <7.0){
        return Color.Yellow
    }
    return Color.Green

}
