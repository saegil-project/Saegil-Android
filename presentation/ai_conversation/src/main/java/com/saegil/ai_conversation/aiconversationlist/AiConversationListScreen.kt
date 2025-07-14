package com.saegil.ai_conversation.aiconversationlist

import android.util.Log
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.ai_conversation.aiconversationlist.components.CharacterCard
import com.saegil.ai_conversation.SaegilCharacter
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme

@Composable
fun AiConversationListScreen(
    modifier: Modifier = Modifier,
    onCharacterClick: (String) -> Unit = { character -> },
    viewModel: AiConversationListViewModel = hiltViewModel(),
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier
                .padding()
                .verticalScroll(rememberScrollState())
        ) {
            SaegilTitleText(
                "AI 전화 회화",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))

            CharacterCard(
                modifier = Modifier,
                character = SaegilCharacter.SAEROM,
                onClick = {
                    onCharacterClick(SaegilCharacter.SAEROM.name)
                })

            Spacer(modifier = Modifier.height(30.dp))

            CharacterCard(modifier = Modifier,
                character = SaegilCharacter.GILDONG,
                onClick = {
                    onCharacterClick(SaegilCharacter.GILDONG.name)
                }
            )
        }
    }
}

@Composable
@Preview(name = "Learning")
private fun LearningScreenPreview() {
    SaegilAndroidTheme {
        Surface {
            AiConversationListScreen()
        }
    }
}

