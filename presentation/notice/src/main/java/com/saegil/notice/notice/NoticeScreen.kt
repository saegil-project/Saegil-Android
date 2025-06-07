package com.saegil.notice.notice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.component.SourceChip
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.caption
import com.saegil.designsystem.theme.h1
import com.saegil.domain.model.Notice
import com.saegil.notice.notice.NoticeUiState.Loading
import com.saegil.notice.notice.NoticeUiState.Success
import com.saegil.notice.notice.component.SearchToolBar

@Composable
fun NoticeScreen(
    navigateToWebView: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoticeViewModel = hiltViewModel(),
) {

    val feedState by viewModel.feedUiState.collectAsStateWithLifecycle()
    val feedResource = (feedState as? Success)?.feeds?.collectAsLazyPagingItems()
    val selectedIndex by viewModel.organization.collectAsStateWithLifecycle()
    val searchQuery by viewModel.query.collectAsStateWithLifecycle()

    NoticeScreen(
        feedState = feedState,
        feedResource = feedResource,
        onChipSelect = viewModel::setSourceFilter,
        searchQuery = searchQuery,
        onSearchTriggered = viewModel::onSearchTriggered,
        selectedIndex = selectedIndex?.toInt(),
        navigateToWebView = navigateToWebView,
        modifier = modifier
    )

}

@Composable
internal fun NoticeScreen(
    feedState: NoticeUiState,
    feedResource: LazyPagingItems<Notice>?,
    onChipSelect: (Int?) -> Unit,
    searchQuery: String,
    onSearchTriggered: (String) -> Unit,
    selectedIndex: Int?,
    navigateToWebView: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {
            SaegilTitleText(
                "공지사항 모아보기",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SearchToolBar(
                searchQuery = searchQuery,
                onSearchTriggered = onSearchTriggered,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            SourceFilterChips(
                onChipSelect = onChipSelect,
                selectedIndex = selectedIndex,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 8.dp)
            )
            when (feedState) {
                Loading -> LoadingState()
                is Success -> NoticesList(
                    feedResource = feedResource,
                    navigateToWebView = navigateToWebView
                )
            }
        }
    }
}

@Composable
fun SourceFilterChips(
    onChipSelect: (Int?) -> Unit,
    selectedIndex: Int?,
    modifier: Modifier = Modifier
) {
    val sources = listOf("남북하나재단", "통일부")
    Row(
        modifier = modifier.padding(
            bottom = 12.dp
        )
    ) {
        sources.forEachIndexed { idx, source ->
            val isSelected = selectedIndex == idx + 1
            SourceChip(
                title = source,
                index = if (isSelected) null else idx + 1,
                selected = isSelected,
                onFilterChipClick = onChipSelect,
                modifier = Modifier.padding(end = 8.dp)
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
            .wrapContentSize()
    )
}

@Composable
private fun NoticesList(
    feedResource: LazyPagingItems<Notice>?,
    navigateToWebView: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            newsFeed(
                feedResource = feedResource,
                navigateToWebView = navigateToWebView
            )
        }
    }
}

fun LazyListScope.newsFeed(
    feedResource: LazyPagingItems<Notice>?,
    navigateToWebView: (String) -> Unit
) {
    feedResource?.let {
        items(feedResource.itemCount) { index ->
            val feed = feedResource[index]
            feed?.let {
                Column {
                    ListItem(
                        headlineContent = {
                            Box(
                                modifier = Modifier.padding(
                                    top = 12.dp,
                                    bottom = 25.dp
                                )
                            ) {
                                Text(
                                    text = it.content,
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        },
                        modifier = Modifier
                            .clickable { navigateToWebView(it.webLink) },
                        overlineContent = {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.h1,
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        supportingContent = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Box(
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = it.date,
                                        style = MaterialTheme.typography.caption,
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = it.source,
                                        style = MaterialTheme.typography.caption,
                                    )
                                }
                            }
                        },
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.08f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun AnnouncementScreenPreview() {
    SaegilAndroidTheme {
    }
}

