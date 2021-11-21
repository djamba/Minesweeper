package ru.roman.ishchenko.minesweeper.features.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:03
 */

internal abstract class BaseWorkflowViewModel<S, E, A>(
    initState: S,
    private val reducer: Reducer<S, E, A>
) : ViewModel() {

    private val _viewState = MutableStateFlow<S>(initState)
    val viewState = _viewState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            initState
        )

    fun obtainEvent(event: E) {
        val (newState, action) = reducer.reduce(_viewState.value, event)
        if (newState != null) {
            viewModelScope.launch {
                _viewState.emit(newState)
            }
        }
        if (action != null) {
            viewModelScope.launch {
                handleAction(action)
            }
        }
    }

    protected abstract suspend fun handleAction(action: A)
}
