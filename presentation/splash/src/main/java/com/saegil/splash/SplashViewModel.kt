package com.saegil.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.CheckLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    checkLoginStatusUseCase: CheckLoginStatusUseCase
) : ViewModel() {

    val loginUiState: StateFlow<LoginState> = flow {
        emit(LoginState.Loading)
        delay(1000) // 스플래시 딜레이용
        emit(if (checkLoginStatusUseCase()) LoginState.LoggedIn else LoginState.NotLoggedIn)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginState.Loading
    )

}