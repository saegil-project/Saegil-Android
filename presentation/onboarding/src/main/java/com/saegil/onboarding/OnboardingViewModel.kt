package com.saegil.onboarding


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.saegil.onboarding.OnboardingState.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {

    private val kakaoToken = MutableStateFlow<String?>(null)

    val loginUiState: StateFlow<OnboardingState> = kakaoToken
        .map { tokenValue ->
            if (tokenValue == null) return@map Idle
            kakaoLoginUseCase(tokenValue)
                .map { success ->
                    if (success) Success else Failure
                }
                .onStart { emit(Loading) }
                .first()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Idle
        )

    fun loginWithKakaoToken(kakaoAccessToken: String) {
        kakaoToken.value = kakaoAccessToken
    }

}