package ru.roman.ishchenko.minesweeper.features.game.presentation

import ru.roman.ishchenko.minesweeper.features.base.Reducer
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperState
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameEvent
import java.lang.IllegalStateException

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:25
 */

internal class MinesweeperReducer: Reducer<MinesweeperState, MinesweeperEvent, MinesweeperAction> {

    override fun reduce(state: MinesweeperState, event: MinesweeperEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (event) {
            is NewGameEvent -> reduce()
            is OpenCellEvent -> reduce(state, event)
            is FlagCellEvent -> reduce(state, event)
            is ChaneBoardEvent -> reduce(state, event)
            is WinGameEvent -> reduce(state, event)
            is LoseGameEvent -> reduce(state, event)
            is TimerEvent -> reduce(state, event)
        }

    private fun reduce(): Pair<MinesweeperState?, MinesweeperAction?> =
        StartGameState to NewGameAction

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
            is GameSate -> state.copy(board = event.board) to SaveGameAction
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: WinGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> WinGameState(event.minFound, event.timeLeft) to null
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: LoseGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> LoseGameState(event.minFound, event.timeLeft) to null
            else -> throw IllegalStateException("Wrong state")
        }

    private fun reduce(state: MinesweeperState, event: TimerEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (state) {
            is GameSate -> state.copy(timeLeft = event.timeLeft) to SaveGameAction
            else -> throw IllegalStateException("Wrong state")
        }
}
