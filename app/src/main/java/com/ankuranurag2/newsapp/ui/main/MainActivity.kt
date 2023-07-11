package com.ankuranurag2.newsapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ankuranurag2.newsapp.R
import com.ankuranurag2.newsapp.ui.theme.NewsAppComposeTheme
import com.ankuranurag2.newsapp.ui.webview.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel> { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    NewsScreen(
                        viewModel = viewModel,
                        onItemClick = { url ->
                            val intent = Intent(this, WebViewActivity::class.java)
                            intent.putExtra(WebViewActivity.ARGS_URL_EXTRA, url)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
        viewModel.fetchData()
    }
}


@Composable
fun NewsScreen(
    viewModel: MainViewModel,
    onItemClick: (url: String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.size(100.dp))
        }
    } else {
        val newsList = uiState.value.newsList
        if (newsList.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_error_outline_24),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Red)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Oops! No news to show.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(newsList.size) { index ->
                    val newsData = newsList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 8.dp)
                            .clickable {
                                onItemClick.invoke(newsData.url)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(newsData.urlToImage)
                                .memoryCacheKey(newsData.urlToImage)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .crossfade(true)
                                .build(),
                            contentDescription = "stringResource(R.string.description)",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = newsData.title ?: "",
                                modifier = Modifier.fillMaxSize(),
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = newsData.title ?: "",
                                modifier = Modifier.fillMaxSize(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Divider()
                        }
                    }
                }
            }
        }
    }
}