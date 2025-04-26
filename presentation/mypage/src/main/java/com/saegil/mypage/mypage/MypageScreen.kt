package com.saegil.mypage.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme

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
            SaegilTitleText(
                "마이페이지",
                modifier = Modifier.align(Alignment.CenterHorizontally)
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

