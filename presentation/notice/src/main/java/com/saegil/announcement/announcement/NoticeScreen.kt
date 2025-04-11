package com.saegil.announcement.announcement


import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import com.saegil.announcement.announcement.NoticeUiState.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saegil.announcement.R
import com.saegil.designsystem.component.SourceChip
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body
import com.saegil.designsystem.theme.caption
import com.saegil.designsystem.theme.h1
import com.saegil.domain.model.Notice

@Composable
fun NoticeScreen(
    viewModel: NoticeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
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
        selectedIndex = selectedIndex,
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 25.dp)
    ) {
        SearchToolBar(
            searchQuery = searchQuery,
            onSearchTriggered = onSearchTriggered,
        )
        SourceFilterChips(
            onChipSelect = onChipSelect,
            selectedIndex = selectedIndex,
            modifier = Modifier
        )
        when (feedState) {
            Loading -> LoadingState()
            is Success -> NoticesList(
                feedResource = feedResource,
            )
        }
    }
}

@Composable
fun SearchToolBar(
    searchQuery: String,
    onSearchTriggered: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        value = searchQuery,
        onValueChange = { onSearchTriggered(it) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearchTriggered(searchQuery)
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onKeyEvent {
            if (it.key == Key.Enter) {
                if (searchQuery.isBlank()) return@onKeyEvent false
                onSearchExplicitlyTriggered()
                true
            } else {
                false
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                if (searchQuery.isBlank()) return@KeyboardActions
                onSearchExplicitlyTriggered()
            },
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        placeholder = {
            Text("검색어를 입력하세요")
        }
    )
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
private fun NoticesList(
    feedResource: LazyPagingItems<Notice>?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            newsFeed(feedResource)
        }
    }
}

fun LazyListScope.newsFeed(
    feedResource: LazyPagingItems<Notice>?
) {
    feedResource?.let {
        items(feedResource.itemCount) { index ->
            val feed = feedResource[index]
            val context = LocalContext.current
            feed?.let {
                Column {
                    ListItem(
                        headlineContent = {
                            Box(
                                modifier = Modifier.padding(vertical = 14.dp)
                            ) {
                                Text(
                                    text = it.content,
                                    style = MaterialTheme.typography.body,
                                )
                            }
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
                    HorizontalDivider()
                }
            }
        }
    }
}

fun openCustomTab(context: Context, url: String) =
    CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))


@Composable
@Preview(apiLevel = 33)
private fun AnnouncementScreenPreview() {
    SaegilAndroidTheme {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {

        }
    }
}
