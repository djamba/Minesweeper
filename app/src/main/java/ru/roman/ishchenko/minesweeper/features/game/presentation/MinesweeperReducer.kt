package ru.roman.ishchenko.minesweeper.features.game.presentation

import ru.roman.ishchenko.minesweeper.features.base.Reducer
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperState
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameEvent

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:25
 */

internal class MinesweeperReducer: Reducer<MinesweeperState, MinesweeperEvent, MinesweeperAction> {

    override fun reduce(state: MinesweeperState, event: MinesweeperEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        when (event) {
            is NewGameEvent -> reduce(state, event)
            else -> { null to null } // TODO: remove else
        }

    private fun reduce(state: MinesweeperState, event: NewGameEvent): Pair<MinesweeperState?, MinesweeperAction?> =
        StartGameState to NewGameAction
}
