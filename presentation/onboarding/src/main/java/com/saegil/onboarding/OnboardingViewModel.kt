package com.saegil.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.saegil.onboarding.OnboardingState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {

    private val kakaoAccessToken = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val loginUiState: StateFlow<OnboardingState> = kakaoAccessToken
        .flatMapLatest { accessToken ->
            accessToken?.let {
                kakaoLoginUseCase(accessToken)
                    .map { success -> if (success) Success else Failure }
                    .onStart { emit(Loading) }
            } ?: flowOf(Idle)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Idle
        )

    fun loginWithKakaokakaoAccessToken(accessToken: String) {
        kakaoAccessToken.value = accessToken
    }

}