package ru.roman.ishchenko.minesweeper.features.game.middleware

import ru.roman.ishchenko.minesweeper.features.base.Middleware
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.FlagCellAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.OpenCellAction

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:43
 */

internal class GameMiddleware: Middleware<MinesweeperEvent, MinesweeperAction> {

    suspend fun handleAction(action: NewGameAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }

    suspend fun handleAction(action: OpenCellAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }

    suspend fun handleAction(action: FlagCellAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }
}
