package com.saegil.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.saegil.splash.LoginState.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {

}