package com.saegil.map.map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h2
import com.saegil.domain.model.Organization
import com.saegil.domain.model.Recruitment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizationBottomSheet(
    organization: Organization,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier
            .padding(bottom = 80.dp)
            .navigationBarsPadding(),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        tonalElevation = 0.dp,
        scrimColor = MaterialTheme.colorScheme.background.copy(alpha = 0f),
        dragHandle = null
    ) {
        OrganizationContent(
            organization = organization,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecruitmentBottomSheet(
    recruitment: Recruitment,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier
            .padding(bottom = 80.dp)
            .navigationBarsPadding(),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        tonalElevation = 0.dp,
        scrimColor = MaterialTheme.colorScheme.background.copy(alpha = 0f),
        dragHandle = null
    ) {
        RecruitmentContent(
            recruitment = recruitment,
        )
    }
}

@Composable
private fun RecruitmentContent(
    recruitment: Recruitment,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 36.dp),
    ) {
        Text(
            text = recruitment.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "근무지명", recruitment.name)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "근무시간", recruitment.workTime)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "임금", recruitment.pay)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "지원 기간", recruitment.recruitmentPeriod)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "웹 링크", recruitment.webLink)
    }
}

@Composable
private fun OrganizationContent(
    organization: Organization,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 36.dp),
    ) {
        Text(
            text = organization.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "운영시간", organization.operatingHours) //todo 운영 시간, 전화번호 없음 이슈
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "전화번호", organization.telephoneNumber)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(modifier = Modifier, "주소", organization.address)
    }
}

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Row(modifier = modifier) {
        Text(
            text = "$title | ",
            style = MaterialTheme.typography.body2.copy(fontWeight = Bold)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
fun OrganizationBottomSheetPreview() {
    SaegilAndroidTheme {
        Surface {
            Column {
                OrganizationContent(
                    Organization(
                        id = 1,
                        name = "Organization 1",
                        operatingHours = "Mon-Fri: 9am-5pm",
                        address = "서울시 상도로 369",
                        latitude = 37.5665,
                        longitude = 126.9780,
                        telephoneNumber = "02-1234-5678",
                        distance = 300.0,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OrganizationContent(
                    Organization(
                        id = 1,
                        name = "한빛종합사회복지관",
                        operatingHours = "09:00 ~ 17:00",
                        address = "서울특별시 양천구 신월로11길 16 (신월동, 한빛종합사회복지관)",
                        latitude = 37.5665,
                        longitude = 126.9780,
                        telephoneNumber = "02-2690-8762~4",
                        distance = 300.0,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                RecruitmentContent(
                    Recruitment(
                        id = 1,
                        name = "연동지역아동센터 생활복지사 구인",
                        workPlaceName = "연동지역아동센터",
                        workTime = "(오전) 9시 00분 ~ (오후) 6시 00분 (주 5일 근무)",
                        pay = "월 200만원",
                        recruitmentPeriod = "2025-04-29 ~ 2025-05-14",
                        webLink = "http://ceu.ssis.go.kr/",
                        address = "서울특별시 양천구 신월로11길 16 (신월동, 한빛종합사회복지관)",
                        latitude = 37.5190344,
                        longitude = 126.8402373,
                        distanceMeters = 312.5
                    )
                )
            }
        }
    }
}