package com.saegil.mypage.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.mypage.mypage.component.SettingMenuItem

@Composable
fun MypageScreen(
    modifier: Modifier = Modifier,
    viewModel: MypageViewModel = hiltViewModel()
) {

    MypageScreen(modifier)

}

@Composable
internal fun MypageScreen(
    s : Int = 1,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 30.dp
            )
        ) {
            SaegilTitleText(
                "마이페이지",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            SettingMenuItem(
                "개인정보처리방침",
                {},
                isArrow = true
            )
            SettingMenuItem(
                "이용약관",
                {},
                isArrow = true
            )
            SettingMenuItem(
                "로그아웃",
                {},
            )
            SettingMenuItem(
                "회원탈퇴",
                {},
            )
        }
    }
}

@Composable
@Preview(name = "Mypage", apiLevel = 33)
private fun MypageScreenPreview() {
    SaegilAndroidTheme {
        MypageScreen(
            2,
        )
    }
}

