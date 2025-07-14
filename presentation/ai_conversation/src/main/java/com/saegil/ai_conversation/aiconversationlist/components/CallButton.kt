package com.saegil.ai_conversation.aiconversationlist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.h2

@Composable
fun CallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF31C015), // #31C015
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
    ) {
        Text(
            text = "전화 걸기",
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.h2,
        )
    }

}


@Preview
@Composable
fun CallButtonPreview() {
    CallButton(onClick = {})
}