package com.saegil.loglist

import androidx.lifecycle.ViewModel
import com.saegil.domain.usecase.GetScenarioLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LogListViewModel @Inject constructor(
    getScenarioLogUseCase: GetScenarioLogUseCase
): ViewModel() {

    //val logUiState: StateFlow<LogListUiState>

}