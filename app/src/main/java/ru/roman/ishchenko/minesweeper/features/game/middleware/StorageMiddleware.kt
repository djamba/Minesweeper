package ru.roman.ishchenko.minesweeper.features.game.middleware

import ru.roman.ishchenko.minesweeper.features.base.Middleware
import ru.roman.ishchenko.minesweeper.features.game.model.LoadGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.SaveGameAction

/**
 * User: roman
 * Date: 20.11.2021
 * Time: 0:31
 */

internal class StorageMiddleware: Middleware<MinesweeperEvent, MinesweeperAction> {

    suspend fun handleAction(action: LoadGameAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }

    suspend fun handleAction(action: SaveGameAction): MinesweeperEvent {
        TODO("Not yet implemented")
    }
}
