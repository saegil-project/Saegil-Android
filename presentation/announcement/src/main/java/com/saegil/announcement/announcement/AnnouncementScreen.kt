package com.saegil.announcement.announcement


import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.saegil.announcement.announcement.AnnouncementUiState.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.theme.body
import com.saegil.designsystem.theme.caption
import com.saegil.designsystem.theme.h1

@Composable
fun AnnouncementScreen(
    viewModel: AnnouncementViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    val feedState by viewModel.feedUiState.collectAsStateWithLifecycle()

    AnnouncementScreen(
        feedState = feedState,
        modifier = modifier
    )

}

@Composable
internal fun AnnouncementScreen(
    feedState: AnnouncementUiState,
    modifier: Modifier = Modifier,
) {
    when (feedState) {
        Loading -> LoadingState(modifier)
        is Success -> if (feedState.feed.isNotEmpty()) {
            AnnouncementsList(  
                feedState = feedState,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
) {
    SaegilLoadingWheel(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    )
}

@Composable
fun SaegilLoadingWheel(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.width(48.dp),//부모로부터 받은 modifier를 중앙배치용으로 사용
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
private fun AnnouncementsList(
    feedState: AnnouncementUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            newsFeed(feedState)
        }
    }
}

fun LazyListScope.newsFeed(//최종적으로 core의 ui모듈로 보내버릴예정
    feedState: AnnouncementUiState
) {
    when (feedState) {
        Loading -> Unit
        is Success -> {
            items(feedState.feed) {
                val context = LocalContext.current

                ListItem(
                    headlineContent = {
                        Text(
                            text = it.content,
                            style = MaterialTheme.typography.body,
                        )
                    },
                    modifier = Modifier
                        .clickable { openCustomTab(context, it.webLink) },
                    overlineContent = {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.h1,
                        )
                    },
                    supportingContent = {
                        Column {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Start)
                            ) {
                                Text(
                                    text = it.date,
                                    style = MaterialTheme.typography.caption,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.End)
                            ) {
                                Text(
                                    text = it.source,
                                    style = MaterialTheme.typography.caption,
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

fun openCustomTab(context: Context, url: String) =
    CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))


@Composable
@Preview(name = "Announcement")
private fun AnnouncementScreenPreview() {
    AnnouncementScreen(
    )
}

val a = ArrayList<Pair<Int,Int>>()

//@Composable
//@Preview
//private fun FeedPreview() {
//    SaegilAndroidTheme {
//        LazyColumn(
//            modifier = Modifier
//                .padding(horizontal = 8.dp)
//        ) {
//            newsFeed(
//                Success(
//                    listOf(
//                        Notice(
//                            "이름",
//                            "내용",
//                            "날짜",
//                            "기관",
//                            "링크"
//                        )
//                    )
//                )
//            )
//        }
//    }
//}
//
