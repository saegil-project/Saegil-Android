package com.saegil.designsystem.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h2

@Composable
fun SaegilTabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val textColor = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        modifier = modifier.height(48.dp),
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.h2,
        )
    }
}

@Composable
@Preview(apiLevel = 33)
fun TabButtonPreview() {
    SaegilAndroidTheme {
        SaegilTabButton(text = "복지시설", isSelected = true, onClick = {})
    }
}

@Composable
@Preview(apiLevel = 33)
fun TabButtonPreview2() {
    SaegilAndroidTheme {
        SaegilTabButton(text = "채용 정보", isSelected = false, onClick = {})
    }
}