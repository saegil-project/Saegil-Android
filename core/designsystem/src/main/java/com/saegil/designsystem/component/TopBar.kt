package com.saegil.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun LogoTitleTopBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_saegil_logo),
            contentDescription = "앱 로고",
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(54.dp)
        )
        Text(
            title,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun BackTitleTopBar(
    title: String = "",
    onBackClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .size(70.dp),
        contentAlignment = Alignment.Center
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기"
            )
        }
    }
}


@Preview
@Composable
fun TopBarPreview() {
    SaegilAndroidTheme {
        Column {
            LogoTitleTopBar("공지사항")
        }
    }
}

@Preview
@Composable
fun BackTopBarPreview() {
    SaegilAndroidTheme {
        Column {
            BackTitleTopBar("공지사항", onBackClick = {})
            Spacer(modifier = Modifier.padding(10.dp))
            BackTitleTopBar(onBackClick = {})
        }
    }
}

