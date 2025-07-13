package com.saegil.news.newsquiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h3
import com.saegil.domain.model.NewsItem
import com.saegil.news.component.QuizImageButton

@Composable
fun NewsQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsQuizViewModel = hiltViewModel()
) {

}
@Composable
internal fun NewsQuizScreen(
    newsItem: NewsItem,
    quizText: String,
    modifier: Modifier = Modifier,
    onAnswer: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Column {
                VideoPlayer(url = newsItem.imageUrl)

                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = newsItem.title,
                        style = MaterialTheme.typography.h3,
                        maxLines = 2,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = "#${newsItem.topic}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = newsItem.date,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "뉴스 퀴즈",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = quizText,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuizImageButton(answer = true)
                    QuizImageButton(answer = false)
                }
            }
        }
    }
}

@Preview
@Composable
fun NewsQuizScreenPreview() {
    SaegilAndroidTheme {
        NewsQuizScreen(
            newsItem = NewsItem(
                title = "서울 올해 첫 폭염주의보… 밤에도 무더위 계속",
                topic = "날씨",
                date = "2025.06.30",
                imageUrl = ""
            ),
            quizText = "좋아좋아"
        )
    }
}