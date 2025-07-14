package com.saegil.ai_conversation.aiconversationlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h2

@Composable
fun CharacterCard(
    modifier: Modifier=Modifier,
    character: SaegilCharacter,
    onClick: () -> Unit = {}
) {

    Card(modifier = modifier.clickable {
//        onClick()
    }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painterResource(character.img), contentDescription = "새봄 프로필 사진")

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(character.nickname, style = MaterialTheme.typography.h2, color = Color.Blue)
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


@Preview
@Composable
fun CharacterCardPreview() {
    Column {
        CharacterCard(modifier = Modifier, SaegilCharacter.SAEROM)

        CharacterCard(modifier = Modifier, SaegilCharacter.GILDONG)
    }
}