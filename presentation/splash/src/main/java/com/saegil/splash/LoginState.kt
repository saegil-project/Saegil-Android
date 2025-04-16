package com.saegil.splash

sealed interface LoginState {
    data object Loading : LoginState
    data object LoggedIn : LoginState
    data object NotLoggedIn : LoginState
}