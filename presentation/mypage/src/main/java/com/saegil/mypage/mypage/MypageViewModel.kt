package com.saegil.mypage.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetTokenUseCase
import com.saegil.domain.usecase.GetUserNameInfoUseCase
import com.saegil.domain.usecase.LogoutUseCase
import com.saegil.domain.usecase.WithdrawalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawalUseCase: WithdrawalUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    getUserNameInfoUseCase: GetUserNameInfoUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<MypageUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val mypageUiState: StateFlow<MypageUiState> = getUserNameInfoUseCase()
        .map<String,MypageUiState>(MypageUiState::Success)
        .onStart { emit(MypageUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MypageUiState.Loading,
        )

    fun logout() {
        viewModelScope.launch {
            getTokenUseCase().collect { token ->
                token?.let {
                    _uiEvent.emit(if (logoutUseCase(it)) MypageUiEvent.SuccessLogout else MypageUiEvent.FailureLogout)
                }
            }
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            getTokenUseCase().collect { token ->
                token?.let {
                    _uiEvent.emit(if (withdrawalUseCase(it)) MypageUiEvent.SuccessWithdrawal else MypageUiEvent.FailureWithdrawal)
                }
            }
        }
    }
}