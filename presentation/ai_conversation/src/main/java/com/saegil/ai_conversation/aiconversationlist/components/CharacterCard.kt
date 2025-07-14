package com.saegil.ai_conversation.aiconversationlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.ai_conversation.SaegilCharacter
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h2

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: SaegilCharacter,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .background(MaterialTheme.colorScheme.background)
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.scrim,
                shape = RoundedCornerShape(4.dp) // ← 코너를 4dp만큼 둥글게
            )
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(character.img), contentDescription = "새봄 프로필 사진",
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 24.dp,
                        bottom = 18.dp,
                        end = 30.dp
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        character.nickname,
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(character.gender, style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(character.personality, style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(character.description, style = MaterialTheme.typography.body2)
                }
            }

            CallButton(onClick = {
                onClick()
            }, modifier = Modifier.padding(20.dp))
        }
    }
}


@Preview
@Composable
fun CharacterCardPreview() {
    SaegilAndroidTheme {
        Column {
            CharacterCard(modifier = Modifier, SaegilCharacter.SAEROM)

            CharacterCard(modifier = Modifier, SaegilCharacter.GILDONG)
        }
    }
}