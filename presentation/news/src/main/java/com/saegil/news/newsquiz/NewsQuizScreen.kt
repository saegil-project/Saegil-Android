package com.saegil.news.newsquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h1
import com.saegil.designsystem.theme.h3
import com.saegil.designsystem.theme.h2
import com.saegil.domain.model.Quiz
import com.saegil.news.component.QuizImageButton

@Composable
fun NewsQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsQuizViewModel = hiltViewModel()
) {

    val newsQuizState by viewModel.newsQuizUiState.collectAsStateWithLifecycle()

    NewsQuizScreen(
        newsQuizState = newsQuizState,
        modifier = modifier
    )
}

@Composable
internal fun NewsQuizScreen(
    newsQuizState: NewsQuizUiState,
    modifier: Modifier = Modifier,
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
            when (newsQuizState) {
                NewsQuizUiState.Loading -> LoadingState()

                is NewsQuizUiState.Success -> QuizContent(
                    quiz = newsQuizState.quiz,
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(), // 화면 전체를 채움
        contentAlignment = Alignment.Center // 가운데 정렬
    ) {
        SaegilLoadingWheel(
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Composable
internal fun QuizContent(
    quiz: Quiz,
    modifier: Modifier = Modifier,
) {

    var showAnswer by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
        .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 35.dp, vertical = 16.dp)
        ) {
            YoutubePlayer(url = quiz.videoUrl)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = quiz.title,
                style = MaterialTheme.typography.h3,
                lineHeight = 1.2.em,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = "#${quiz.category}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = quiz.date,
                    style = MaterialTheme.typography.body2,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp, vertical = 35.dp)
            ) {
                Text(
                    text = "뉴스 퀴즈",
                    style = MaterialTheme.typography.h3,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = quiz.question,
                    style = MaterialTheme.typography.h2,
                )

                Spacer(modifier = Modifier.height(24.dp))


                if (!showAnswer) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(36.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        QuizImageButton(
                            answer = true,
                            onClick = { showAnswer = true },
                            modifier = Modifier.weight(1f),
                        )
                        QuizImageButton(
                            answer = false,
                            onClick = { showAnswer = true },
                            modifier = Modifier.weight(1f),
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(12.dp))

                    Column {
                        Text(
                            text = "정답 : ${quiz.answer}",
                            style = MaterialTheme.typography.h1,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "해설 : " +quiz.explanation,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }
    }
}