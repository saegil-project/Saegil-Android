package com.saegil.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.R
import com.saegil.designsystem.component.SourceChip
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h2
import com.saegil.designsystem.theme.h3
import com.saegil.news.component.CategoryConstants

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val newsState by viewModel.newsUiState.collectAsStateWithLifecycle()

    NewsScreen(
        newsUiState = newsState,
        modifier = modifier,
        onNextButtonClick = viewModel::savePreferredTopics,
    )
}

@Composable
internal fun NewsScreen(
    newsUiState: NewsUiState,
    modifier: Modifier = Modifier,
    onNextButtonClick: (List<String>) -> Unit = {},
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {
            SaegilTitleText(
                "뉴스",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            when (newsUiState) {
                NewsUiState.Loading -> LoadingState()
                NewsUiState.NoTopics -> NoTopicsState(
                    onNextButtonClick = onNextButtonClick
                )

                is NewsUiState.Success -> TopicsState(
                    topics = newsUiState.preferredTopics,
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
    modifier: Modifier = Modifier,
    onNextButtonClick: (List<String>) -> Unit = {},
) {

    val allTopics = CategoryConstants.preferredTopics
    var selectedTopics by remember { mutableStateOf<List<String>>(emptyList()) }
    val isSelectAll = selectedTopics.size == allTopics.size

    fun onTopicToggle(topic: String) {
        selectedTopics = if (topic in selectedTopics) {
            selectedTopics - topic
        } else {
            selectedTopics + topic
        }
    }

    fun onSelectAllToggle(selectAll: Boolean) {
        selectedTopics = if (selectAll) {
            allTopics
        } else {
            emptyList()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            allTopics.forEachIndexed { index, topic ->
                val isSelected = topic in selectedTopics
                SourceChip(
                    title = topic,
                    index = index,
                    selected = isSelected,
                    onFilterChipClick = {
                        onTopicToggle(topic)
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
                containerColor = if(selectedTopics.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .height(45.dp)
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
    topics: List<String>,
    modifier: Modifier = Modifier,
) {

}


@Composable
@Preview(apiLevel = 33)
fun NewsScreenPreview() {
    SaegilAndroidTheme {
        NewsScreen(NewsUiState.NoTopics)
    }
}
