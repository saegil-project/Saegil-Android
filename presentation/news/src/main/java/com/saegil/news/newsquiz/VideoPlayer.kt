package com.saegil.news.newsquiz

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayer(
    url: String,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val videoId = extractYoutubeVideoId(url) ?: return
    val context = LocalContext.current

    val youtubePlayerView = remember {
        YouTubePlayerView(context).apply {
            lifecycleOwner.lifecycle.addObserver(this)
        }
    }

    DisposableEffect(videoId, lifecycleOwner) {
        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                // 현재 재생 시간을 추적하고 싶을 때 사용
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState) {
                // 플레이어 상태 변화 감지 (재생, 일시정지, 버퍼링 등)
            }
        }

        youtubePlayerView.addYouTubePlayerListener(listener)

        onDispose {
            youtubePlayerView.removeYouTubePlayerListener(listener)
        }
    }

    AndroidView(
        factory = {
            FrameLayout(context).apply {
                clipToOutline = true
                background = ContextCompat.getDrawable(context, android.R.color.transparent)
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View, outline: Outline) {
                        val radius = 32f
                        outline.setRoundRect(0, 0, view.width, view.height, radius)
                    }
                }
                addView(youtubePlayerView)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    )
}

fun extractYoutubeVideoId(youtubeUrl: String): String? {
    return try {
        val uri = youtubeUrl.toUri()
        when {
            uri.host?.contains("youtu.be") == true -> uri.lastPathSegment
            uri.host?.contains("youtube.com") == true -> uri.getQueryParameter("v")
            else -> null
        }
    } catch (e: Exception) {
        null
    }
}