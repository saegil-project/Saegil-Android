package com.saegil.news.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.R
import com.saegil.designsystem.component.SourceChip
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h2
import com.saegil.designsystem.theme.h3
import com.saegil.domain.model.Interest
import com.saegil.domain.model.NewsItem
import com.saegil.news.component.NewsItem

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    navigateToQuiz: (Long) -> Unit = {},
) {

    val newsState by viewModel.newsUiState.collectAsStateWithLifecycle()

    NewsScreen(
        newsUiState = newsState,
        modifier = modifier,
        onNextButtonClick = viewModel::savePreferredTopics,
        onTopicSelectClick = viewModel::clearPreferredTopics,
        navigateToQuiz = navigateToQuiz
    )
}

@Composable
internal fun NewsScreen(
    newsUiState: NewsUiState,
    modifier: Modifier = Modifier,
    onNextButtonClick: (List<Interest>) -> Unit = {},
    onTopicSelectClick: () -> Unit = {},
    navigateToQuiz: (Long) -> Unit = {},
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize(),
        ) {
            SaegilTitleText(
                "뉴스",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            when (newsUiState) {
                NewsUiState.Loading -> LoadingState()
                is NewsUiState.NoTopics -> NoTopicsState(
                    interests = newsUiState.categories,
                    onNextButtonClick = onNextButtonClick
                )

                is NewsUiState.Success -> TopicsState(
                    newsItems = newsUiState.newsItems,
                    onTopicSelectClick = onTopicSelectClick,
                    navigateToQuiz = navigateToQuiz
                )
            }
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
private fun NoTopicsState(
    interests: List<Interest>,
    modifier: Modifier = Modifier,
    onNextButtonClick: (List<Interest>) -> Unit = {},
) {

    var selectedTopics by remember { mutableStateOf<List<Interest>>(emptyList()) }
    val isSelectAll = selectedTopics.size == interests.size

    fun onTopicToggle(topic: Interest) {
        selectedTopics = if (topic in selectedTopics) {
            selectedTopics - topic
        } else {
            selectedTopics + topic
        }
    }

    fun onSelectAllToggle(selectAll: Boolean) {
        selectedTopics = if (selectAll) {
            interests
        } else {
            emptyList()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.img_wonder),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "어떤 뉴스 주제를 선호하시나요?",
            style = MaterialTheme.typography.h2,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "선택한 주제 위주의 뉴스를 추천해드릴게요.",
            style = MaterialTheme.typography.body2,
        )

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            interests.forEachIndexed { index, category ->
                val isSelected = category in selectedTopics
                SourceChip(
                    title = category.name,
                    index = index,
                    selected = isSelected,
                    onFilterChipClick = {
                        onTopicToggle(category)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelectAll,
                onCheckedChange = { onSelectAllToggle(it) }
            )
            Text(
                text = "전체 선택",
                style = MaterialTheme.typography.h3,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNextButtonClick(selectedTopics) },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTopics.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .height(45.dp)
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "다음",
                style = MaterialTheme.typography.h2,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TopicsState(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier,
    onTopicSelectClick: () -> Unit = {},
    navigateToQuiz: (Long) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable(onClick = onTopicSelectClick),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pin),
                contentDescription = "뉴스 주제 선택",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(MaterialTheme.typography.h2.fontSize.value.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "뉴스 주제 선택",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.h3,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        ) {
            items(newsItems) { item ->
                NewsItem(
                    title = item.title,
                    topic = item.category,
                    date = item.date,
                    imageUrl = item.thumbnailUrl,
                    onClick = { navigateToQuiz(item.id) }
                )
            }
        }
    }
}