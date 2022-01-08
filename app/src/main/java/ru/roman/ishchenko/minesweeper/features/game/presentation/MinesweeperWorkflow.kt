package ru.roman.ishchenko.minesweeper.features.game.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.roman.ishchenko.minesweeper.features.base.BaseWorkflowViewModel
import ru.roman.ishchenko.minesweeper.features.game.middleware.GameMiddleware
import ru.roman.ishchenko.minesweeper.features.game.middleware.StorageMiddleware
import ru.roman.ishchenko.minesweeper.features.game.middleware.TimerMiddleware
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.StartGameState
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperState
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.OpenCellAction
import javax.inject.Inject

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 21:57
 */

@HiltViewModel
internal class MinesweeperWorkflow @Inject constructor(
    minesweeperReducer: MinesweeperReducer,
    private val gameMiddleware: GameMiddleware,
    private val timerMiddleware: TimerMiddleware,
    private val storageMiddleware: StorageMiddleware
) : BaseWorkflowViewModel<MinesweeperState, MinesweeperEvent, MinesweeperAction>(StartGameState, minesweeperReducer) {

    private var timerJob: Job? = null

    override suspend fun handleAction(action: MinesweeperAction) {
        when (action) {
            is NewGameAction -> {
                val newGameEvent = gameMiddleware.handleAction(action)
                obtainEvent(newGameEvent)
                timerJob = viewModelScope.launch {
                    timerMiddleware.handleAction(action).collect { event ->
                        obtainEvent(event)
                    }
                }
            }
            is OpenCellAction -> {
                val event = gameMiddleware.handleAction(action)
                obtainEvent(event)
            }
            is FlagCellAction -> {
                val event = gameMiddleware.handleAction(action)
                obtainEvent(event)
            }
            is LoadGameAction -> {
                val event = storageMiddleware.handleAction(action)
                obtainEvent(event)
            }
            is SaveGameAction -> {
                val event = storageMiddleware.handleAction(action)
                obtainEvent(event)
            }
            is WinGameAction -> {
                timerJob?.cancel()
                obtainAction(action)
            }
            is LoseGameAction -> {
                timerJob?.cancel()
                obtainAction(action)
            }
        }
    }
}
