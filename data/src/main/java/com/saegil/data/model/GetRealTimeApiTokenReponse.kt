package com.saegil.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RealtimeResponse(
    val id: String,
    val `object`: String,
    val model: String,
    val modalities: List<String>,
    val instructions: String,
    val voice: String,
    val input_audio_format: String,
    val output_audio_format: String,
    val input_audio_transcription: InputAudioTranscription,
    val turn_detection: String,  // null 허용
    val tools: List<String> = emptyList(),
    val tool_choice: String,
    val temperature: Double,
    val speed: Double,
    val tracing: String,  // "auto"
    val max_response_output_tokens: Int,
    val client_secret: ClientSecret
){
    fun toDomain(): String {
        return client_secret.value
    }
}

@Serializable
data class InputAudioTranscription(
    val model: String
)

//@Serializable
//data class TurnDetection(
//    // 현재 예시에선 null이므로 생략 가능. 나중에 구조 생기면 필드 추가
//)
//
//@Serializable
//data class Tool(
//    // 현재 예시에선 빈 객체 리스트, 추후 구조 생기면 필드 추가
//)

@Serializable
data class ClientSecret(
    val value: String,
    val expires_at: Long
)
