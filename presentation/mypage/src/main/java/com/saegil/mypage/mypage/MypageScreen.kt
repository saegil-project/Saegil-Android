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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.saegil.mypage.R
import com.saegil.ui.util.SaegilToast

@Composable
fun MypageScreen(
    navigateToWebView: (String) -> Unit,
    navigateToOnboarding: () -> Unit,
    navigateToLogList: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MypageViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var showWithdrawalDialog by rememberSaveable { mutableStateOf(false) }
    var showGoodbyeDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MypageUiEvent.SuccessLogout -> {
                    SaegilToast(context, context.getString(R.string.logout_message))
                    navigateToOnboarding()
                }

                is MypageUiEvent.FailureLogout ->
                    SaegilToast(context, context.getString(R.string.logout_failed_message))

                is MypageUiEvent.SuccessWithdrawal -> {
                    SaegilToast(context, context.getString(R.string.withdrawal_message))
                    showGoodbyeDialog = true
                }

                is MypageUiEvent.FailureWithdrawal -> {
                    SaegilToast(context, context.getString(R.string.withdrawal_failed_message))
                    showGoodbyeDialog = false
                }
            }
        }
    }

    MypageScreen(
        showLogoutDialog = showLogoutDialog,
        showWithdrawalDialog = showWithdrawalDialog,
        showGoodbyeDialog = showGoodbyeDialog,
        onLogoutDialogDismissed = { showLogoutDialog = false },
        onWithdrawalDialogDismissed = { showWithdrawalDialog = false },
        onGoodbyeDialogDismissed = {
            showGoodbyeDialog = false
            navigateToOnboarding()
        },
        onLogoutClick = { showLogoutDialog = true },
        onWithdrawalClick = { showWithdrawalDialog = true },
        onLogoutNegativeButtonClick = viewModel::logout,
        onWithdrawalNegativeButtonClick = viewModel::withdrawal,
        onClickTermsOfPrivacy = { navigateToWebView(context.getString(R.string.TermsOfPrivacy)) },
        onClickTermsOfService = { navigateToWebView(context.getString(R.string.TermsOfService)) },
        modifier = modifier
    )

}

@Composable
internal fun MypageScreen(
    showLogoutDialog: Boolean,
    showWithdrawalDialog: Boolean,
    showGoodbyeDialog: Boolean,
    onLogoutDialogDismissed: () -> Unit,
    onWithdrawalDialogDismissed: () -> Unit,
    onGoodbyeDialogDismissed: () -> Unit,
    onLogoutClick: () -> Unit,
    onWithdrawalClick: () -> Unit,
    onLogoutNegativeButtonClick: () -> Unit,
    onWithdrawalNegativeButtonClick: () -> Unit,
    onClickTermsOfPrivacy: () -> Unit,
    onClickTermsOfService: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if (showLogoutDialog) {
        SaegilDialog(
            onDismissRequest = onLogoutDialogDismissed,
            onNegativeButtonClicked = {
                onLogoutNegativeButtonClick()
                onLogoutDialogDismissed()
            },
            onPositiveButtonClicked = onLogoutDialogDismissed,
            positiveButtonText = stringResource(id = R.string.cancel),
            title = stringResource(id = R.string.logout_dialog_title),
            description = stringResource(id = R.string.logout_dialog_description),
            negativeButtonText = stringResource(id = R.string.logout)
        )
    }

    if (showWithdrawalDialog) {
        SaegilDialog(
            onDismissRequest = onWithdrawalDialogDismissed,
            onNegativeButtonClicked = {
                onWithdrawalNegativeButtonClick()
                onWithdrawalDialogDismissed()
            },
            onPositiveButtonClicked = onWithdrawalDialogDismissed,
            positiveButtonText = stringResource(id = R.string.cancel),
            title = stringResource(id = R.string.withdrawal),
            subTitle = stringResource(id = R.string.withdrawal_question),
            description = stringResource(id = R.string.withdrawal_description),
            negativeButtonText = stringResource(id = R.string.withdrawal)
        )
    }

    if (showGoodbyeDialog) {
        SaegilDialog(
            onDismissRequest = onGoodbyeDialogDismissed,
            onPositiveButtonClicked = onGoodbyeDialogDismissed,
            positiveButtonText = stringResource(id = R.string.action_go_home),
            subTitle = stringResource(id = R.string.message_account_deletion_success),
            description = stringResource(id = R.string.message_account_deletion_support)
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
                    onClickTermsOfPrivacy,
                    isArrow = true
                )
                SettingMenuItem(
                    title = "이용약관",
                    onClick = onClickTermsOfService,
                    isArrow = true
                )
                SettingMenuItem(
                    title = "로그아웃",
                    onClick = onLogoutClick,
                )
                SettingMenuItem(
                    title = "회원탈퇴",
                    onClick = onWithdrawalClick,
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
            false,
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}

