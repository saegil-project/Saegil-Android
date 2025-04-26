package com.saegil.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetAccessTokenUseCase
import com.saegil.domain.usecase.IsAccessTokenValidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.saegil.splash.LoginState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SplashViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    isAccessTokenValidUseCase: IsAccessTokenValidUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val loginUiState: StateFlow<LoginState> = getAccessTokenUseCase()
        .flatMapLatest { token ->
            token?.let {
                isAccessTokenValidUseCase(token)
                    .map { isValid ->
                        if (isValid) LoggedIn else NotLoggedIn
                    }
                    .onStart { emit(Loading) }
            } ?: flowOf(NotLoggedIn)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = Loading
        )
}