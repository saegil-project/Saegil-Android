package com.saegil.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.R
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h1


@Composable
fun SaegilTopBar(
    showLogo: Boolean,
    showBackButton: Boolean,
    modifier: Modifier = Modifier,
    title: String = "",
    onBackClick: () -> Unit = {},
) {
    Box(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .size(70.dp),
        contentAlignment = Alignment.Center
    ) {

        title.let {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        if (showLogo) {
            Image(
                painter = painterResource(id = R.drawable.ic_saegil_logo),
                contentDescription = "앱 로고",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(54.dp)
            )
        }

        if (showBackButton) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "뒤로가기"
                )
            }
        }
    }
}


@Preview
@Composable
fun LogoTopBarPreview() {
    SaegilAndroidTheme {
        Column {
            SaegilTopBar(
                showBackButton = false,
                onBackClick = { },
                showLogo = true,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun BackTitleTopBarPreview() {
    SaegilAndroidTheme {
        Column {
            SaegilTopBar(
                title = "공지사항",
                showBackButton = true,
                showLogo = false,
                onBackClick = {},
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun BlackBackTitleTopBarPreview() {
    SaegilAndroidTheme {
        Column {
            SaegilTopBar(
                title = "마트에 갔을 때",
                showLogo = false,
                showBackButton = true,
                onBackClick = {},
                modifier = Modifier
            )
        }
    }
}


