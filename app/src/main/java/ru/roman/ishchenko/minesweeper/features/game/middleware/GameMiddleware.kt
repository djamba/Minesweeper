package ru.roman.ishchenko.minesweeper.features.game.middleware

import ru.roman.ishchenko.minesweeper.domain.Cell
import ru.roman.ishchenko.minesweeper.features.base.Middleware
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.FlagCellAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.OpenCellAction
import javax.inject.Inject

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:43
 */

internal class GameMiddleware @Inject constructor(): Middleware<MinesweeperEvent, MinesweeperAction> {

    suspend fun handleAction(action: NewGameAction): MinesweeperEvent {
        val board = Array<Array<Cell>>(10) { Array<Cell>(10) { Cell() } }
        return ChaneBoardEvent(board)
    }

    suspend fun handleAction(action: OpenCellAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }

    suspend fun handleAction(action: FlagCellAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }
}
