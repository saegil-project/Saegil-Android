package com.saegil.learning.learning

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h1
import com.saegil.designsystem.theme.h2
import com.saegil.learn.R

@Composable
fun LearningScreen(
    modifier: Modifier = Modifier,
//    state: LearingState,
//    actions: LearingActions
    scenarioNumber: String,
    scenarioTitle: String,
    content: String,
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "상황 $scenarioNumber",
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 20.dp, bottom = 14.dp)
            )
            Text(
                text = scenarioTitle,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colorScheme.primary
            )

            SaegilCharacter(
                modifier = Modifier.padding(top = 117.dp),
                charactorEmotion = SaegilCharactor.NORMAL
            )

            Text(
                text = content,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 30.dp),
                textAlign = TextAlign.Center
            )

            RecordButton(modifier = Modifier.padding(top = 100.dp))
        }
    }
}

@Composable
fun SaegilCharacter(
    modifier: Modifier,
    charactorEmotion: SaegilCharactor
) {
    Image(
        modifier= modifier.size(134.dp),
        painter = painterResource(id = charactorEmotion.img),
        contentDescription = "새길 캐릭터 이미지"
    )
}

@Composable
fun RecordButton(modifier: Modifier) {
    Image(
        modifier = modifier.size(126.dp),
        painter = painterResource(id = R.drawable.imb_button_record_start),
        contentDescription = "레코드 버튼"
    )
}

@Composable
@Preview(name = "Learning")
private fun LearningScreenPreview() {

    SaegilAndroidTheme {
        Surface {
            LearningScreen(
                modifier = Modifier,
                scenarioNumber = "1",
                scenarioTitle = "새로운 친구를 사귈 때",
                content = "안녕하세요. 처음 뵙겠습니다.\n" +
                        "이름이 어떻게 되세요?"
            )
        }
    }
}

