package com.saegil.mypage.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<MypageUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            _uiEvent.emit(if(logoutUseCase()) MypageUiEvent.SuccessLogout else MypageUiEvent.FailureLogout)
        }
    }
}