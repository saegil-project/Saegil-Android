package com.saegil.mypage.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h2
import com.saegil.designsystem.R

@Composable
fun SettingMenuItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isArrow: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                fontSize = 17.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isArrow) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow),
                    contentDescription = "arrow",
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.08f)
    )
}


@Composable
@Preview(apiLevel = 33)
fun SettingMenuItemPreview() {
    SaegilAndroidTheme {
        SettingMenuItem(
            "개인정보처리방침",
            {}
        )
    }
}