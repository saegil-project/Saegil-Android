package com.saegil.map.map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizationBottomSheet(
    organization: Organization,
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        contentColor = MaterialTheme.colorScheme.background,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        OrganizationContent(organization)
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
        Row {
            Text(
                text = "운영시간 | ",
                style = MaterialTheme.typography.body2.copy(fontWeight = Bold)
            )
            Text(
                text = organization.operatingHours,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "전화번호 | ",
                style = MaterialTheme.typography.body2.copy(fontWeight = Bold)
            )
            Text(
                text = organization.telephoneNumber,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "주소 | ",
                style = MaterialTheme.typography.body2.copy(fontWeight = Bold)
            )
            Text(
                text = organization.address,
                style = MaterialTheme.typography.body2
            )
        }
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
            }
        }
    }
}