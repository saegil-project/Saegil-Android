package com.saegil.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.saegil.designsystem.R
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body1
import com.saegil.designsystem.theme.h1
import com.saegil.designsystem.theme.h2
import com.saegil.designsystem.theme.h3

@Composable
fun SaegilDialog(
    onNegativeButtonClicked: () -> Unit,
    onPositiveButtonClicked: () -> Unit,
    positiveButtonText: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    subTitle: String? = null,
    description: String? = null,
    negativeButtonText: String? = null,
) {
    Dialog(onDismissRequest = onNegativeButtonClicked) {
        Card(
            modifier = modifier
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = 24.dp,
                    horizontal = 12.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                title?.let {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h1,
                        fontSize = 23.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.ic_saegil_logo),
                    contentDescription = "앱 로고",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(42.dp)
                )

                subTitle?.let {
                    Text(
                        text = subTitle,
                        style = MaterialTheme.typography.h3,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(
                                vertical = 8.dp,
                                horizontal = 12.dp
                            )
                            .align(Alignment.Start)
                    )
                }

                description?.let {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body1,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 16.dp,
                            )
                            .padding(
                                horizontal = 12.dp
                            )
                            .align(Alignment.Start)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    negativeButtonText?.let {
                        Button(
                            onClick = onNegativeButtonClicked,
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceDim,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                        ) {
                            Text(
                                text = negativeButtonText,
                                style = MaterialTheme.typography.h2,
                                fontSize = 15.sp,
                            )
                        }
                    }

                    Button(
                        onClick = onPositiveButtonClicked,
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .then(
                                negativeButtonText?.let {
                                    Modifier.weight(1f)
                                } ?: Modifier.fillMaxWidth()
                            )
                    ) {
                        Text(
                            text = positiveButtonText,
                            style = MaterialTheme.typography.h2,
                            fontSize = 15.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun DialogPreview() {
    SaegilAndroidTheme {
        SaegilDialog(
            onNegativeButtonClicked = {},
            onPositiveButtonClicked = {},
            positiveButtonText = "취소",
            title = "회원탈퇴",
            subTitle = "정말 탈퇴하시겠습니까?",
            description = "서비스 탈퇴 시 회원님의 계정 및 지금까지 진행한 학습 대화 내역은 즉시 삭제되며, 복구되지 않습니다.",
            negativeButtonText = "회원탈퇴"
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun LogoDialogPreview() {
    SaegilAndroidTheme {
        SaegilDialog(
            onNegativeButtonClicked = {},
            onPositiveButtonClicked = {},
            positiveButtonText = "홈으로 돌아가기",
            subTitle = "회원탈퇴가 완료되었습니다.",
            description = "새길 서비스에서의 경험을 바탕으로" +
                    "한국에서의 멋진 ‘새길’을 걸어가시길 응원합니다 :)",
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun LogoutDialogPreview() {
    SaegilAndroidTheme {
        SaegilDialog(
            onNegativeButtonClicked = {},
            onPositiveButtonClicked = {},
            positiveButtonText = "취소",
            title = "로그아웃",
            description = "정말 로그아웃하시겠습니까? 기존 학습 대화 내역은 보관됩니다.",
            negativeButtonText = "로그아웃"
        )
    }
}
