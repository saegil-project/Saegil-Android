package com.saegil.ai_conversation.aiconversationlist

import android.util.Log
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    val learningListState by viewModel.stateFlow.collectAsStateWithLifecycle()

    InternalAiConversationListScreen(
        aiConversationListState = learningListState,
        modifier = modifier,
        onCharacterClick = { character ->
            onCharacterClick(character)
        },
    )
}

@Composable
internal fun InternalAiConversationListScreen(
    aiConversationListState: AiConversationListState,
    modifier: Modifier = Modifier,
    onCharacterClick: (String) -> Unit = { character -> },
//    onScenarioClick: (Long, String) -> Unit = { id, name -> },
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            SaegilTitleText(
                "AI 전화 회화",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(60.dp))
            CharacterCard(
                modifier = Modifier,
                SaegilCharacter.SAEROM,
                onClick = {
                    Log.d("screen character", SaegilCharacter.SAEROM.name)
                    onCharacterClick(SaegilCharacter.SAEROM.name)
                })

            Spacer(modifier = Modifier.height(10.dp))

            CharacterCard(modifier = Modifier.clickable {
                onCharacterClick(SaegilCharacter.GILDONG.name)
            }, SaegilCharacter.GILDONG)


//            when (aiConversationListState) {
//                is LearningListUiState.Loading -> {
//                    // TODO: Show loading state
//                }
//                is LearningListUiState.Success -> {
//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize(),
//                        contentPadding = PaddingValues(36.dp),
//                        verticalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        items(learningListState.organizationList) { item ->
//                            ScenarioItem(
//                                name = item.name,
//                                iconImageUrl = item.iconImageUrl,
//                                onClick = { onScenarioClick(item.id, item.name) }
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
@Preview(name = "Learning")
private fun LearningScreenPreview() {
    SaegilAndroidTheme {
        Surface {
            InternalAiConversationListScreen(
                aiConversationListState = AiConversationListState()
            )
        }
    }
}

