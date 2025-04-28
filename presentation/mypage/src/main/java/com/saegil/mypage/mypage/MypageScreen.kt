package com.saegil.mypage.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saegil.designsystem.component.SaegilDialog
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.caption
import com.saegil.mypage.mypage.component.LearningLogButton
import com.saegil.mypage.mypage.component.ProfileCard
import com.saegil.mypage.mypage.component.SettingMenuItem

@Composable
fun MypageScreen(
    modifier: Modifier = Modifier,
    viewModel: MypageViewModel = hiltViewModel()
) {

    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var showWithdrawDialog by rememberSaveable { mutableStateOf(false) }

    MypageScreen(
        showLogoutDialog = showLogoutDialog,
        showWithdrawDialog = showWithdrawDialog,
        onLogoutDialogDismissed = { showLogoutDialog = false },
        onWithdrawDialogDismissed = { showWithdrawDialog = false },
        onLogoutClick = { showLogoutDialog = true },
        onWithdrawClick = { showWithdrawDialog = true },
        modifier = modifier
    )

}

@Composable
internal fun MypageScreen(
    showLogoutDialog: Boolean,
    showWithdrawDialog: Boolean,
    onLogoutDialogDismissed: () -> Unit,
    onWithdrawDialogDismissed: () -> Unit,
    onLogoutClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if(showLogoutDialog) {
        SaegilDialog(
            onNegativeButtonClicked = onLogoutDialogDismissed,
            onPositiveButtonClicked = {},
            positiveButtonText = "취소",
            title = "로그아웃",
            description = "정말 로그아웃하시겠습니까? 기존 학습 대화 내역은 보관됩니다.",
            negativeButtonText = "로그아웃"
        )
    }

    if(showWithdrawDialog) {
        SaegilDialog(
            onNegativeButtonClicked = onWithdrawDialogDismissed,
            onPositiveButtonClicked = {},
            positiveButtonText = "취소",
            title = "회원탈퇴",
            subTitle = "정말 탈퇴하시겠습니까?",
            description = "서비스 탈퇴 시 회원님의 계정 및 지금까지 진행한 학습 대화 내역은 즉시 삭제되며, 복구되지 않습니다.",
            negativeButtonText = "회원탈퇴"
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            SaegilTitleText(
                "마이페이지",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(top = 30.dp)
            ) {
                ProfileCard(
                    name = "김주민",
                )
                LearningLogButton({})
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim)
            )

            Column(
                modifier = Modifier.padding(
                    horizontal = 30.dp
                )
            ) {
                SettingMenuItem(
                    "개인정보처리방침",
                    {},
                    isArrow = true
                )
                SettingMenuItem(
                    title = "이용약관",
                    onClick = {},
                    isArrow = true
                )
                SettingMenuItem(
                    title = "로그아웃",
                    onClick = onLogoutClick,
                )
                SettingMenuItem(
                    title = "회원탈퇴",
                    onClick = onWithdrawClick,
                )
                Text(
                    text = "새길 v1.0",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 15.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
@Preview(name = "Mypage", apiLevel = 33)
private fun MypageScreenPreview() {
    SaegilAndroidTheme {
        MypageScreen(
            false,
            false,
            {},
            {},
            {},
            {}
        )
    }
}

