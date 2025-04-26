package com.saegil.mypage.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h1

@Composable
fun MypageScreen(
    modifier: Modifier = Modifier,
//    state: MypageState,
//    actions: MypageActions
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            Text(
                text = "마이페이지",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
@Preview(name = "Mypage")
private fun MypageScreenPreview() {
    SaegilAndroidTheme {
        Surface {
            MypageScreen(
//        state = MypageState(),
//        actions = MypageActions()
            )
        }
    }
}

