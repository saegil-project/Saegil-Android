package com.seagil.mypage.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun MypageScreen(
//    state: MypageState,
//    actions: MypageActions
) {
    // TODO UI Rendering
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "마이페이지 화면", fontSize = 24.sp)
    }
}

@Composable
@Preview(name = "Mypage")
private fun MypageScreenPreview() {
    MypageScreen(
//        state = MypageState(),
//        actions = MypageActions()
    )
}

