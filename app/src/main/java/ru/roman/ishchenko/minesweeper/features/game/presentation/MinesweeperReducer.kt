package ru.roman.ishchenko.minesweeper.features.game.presentation

import ru.roman.ishchenko.minesweeper.features.base.Reducer
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperState
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameEvent
import ru.roman.ishchenko.minesweeper.features.game.settings.MediumSettings
import java.lang.IllegalStateException
import javax.inject.Inject

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:25
 */

internal class MinesweeperReducer @Inject constructor(): Reducer<MinesweeperState, MinesweeperEvent, MinesweeperAction> {

    override fun reduce(state: MinesweeperState, event: MinesweeperEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (event) {
            is NewGameEvent -> reduce()
            is OpenCellEvent -> reduce(state, event)
            is FlagCellEvent -> reduce(state, event)
            is ChaneBoardEvent -> reduce(state, event)
            is WinGameEvent -> reduce(state, event)
            is LoseGameEvent -> reduce(state, event)
            is TimerEvent -> reduce(state, event)
            is SuccessSaveGameEvent -> reduce(state, event)
            is ErrorSaveGameEvent -> reduce(state, event)
        }

    private fun reduce(): Pair<MinesweeperState?, MinesweeperAction?> =
        StartGameState to NewGameAction(MediumSettings)

    private fun reduce(state: MinesweeperState, event: OpenCellEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> null to OpenCellAction(event.x, event.y)
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: FlagCellEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> null to FlagCellAction(event.x, event.y)
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: ChaneBoardEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is StartGameState -> GameSate(board = event.board, 0, 0) to SaveGameAction
            is GameSate -> GameSate(board = event.board, state.mineFlagged + event.mineFlagged, state.timeLeft) to SaveGameAction
            is WinGameState -> state to null
            is LoseGameState -> state to null
        }

    private fun reduce(state: MinesweeperState, event: WinGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> WinGameState(event.board, event.minFound, state.timeLeft) to WinGameAction
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: LoseGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> LoseGameState(event.board, event.minFound, state.timeLeft) to LoseGameAction
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: TimerEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> state.copy(timeLeft = event.timeLeft) to SaveGameAction
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: SuccessSaveGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> state to null
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: ErrorSaveGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> state to null
            else -> throw IllegalStateException("Wrong state")
        }
}
