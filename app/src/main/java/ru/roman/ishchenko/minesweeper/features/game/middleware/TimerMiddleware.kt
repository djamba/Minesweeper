package ru.roman.ishchenko.minesweeper.features.game.middleware

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import ru.roman.ishchenko.minesweeper.features.base.Middleware
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.TimerEvent
import javax.inject.Inject

/**
 * User: roman
 * Date: 20.11.2021
 * Time: 0:34
 */

internal class TimerMiddleware @Inject constructor(): Middleware<MinesweeperEvent, MinesweeperAction> {

    fun handleAction(action: NewGameAction): Flow<TimerEvent> = flow {
        var time = 0
        while (currentCoroutineContext().isActive) {
            delay(1000)
            time += 1000
            emit(TimerEvent(time))
        }
    }
}
