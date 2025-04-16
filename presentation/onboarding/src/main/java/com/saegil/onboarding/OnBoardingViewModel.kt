package com.saegil.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.saegil.onboarding.OnBoardingState.*

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<OnBoardingState>(Idle)
    val loginUiState: StateFlow<OnBoardingState> = _loginUiState.asStateFlow()

    fun kakaoLogin(context: Context) {
        viewModelScope.launch {
            _loginUiState.value = Loading
            _loginUiState.value = if (kakaoLoginUseCase(context)) Success else Failure
        }
    }

}