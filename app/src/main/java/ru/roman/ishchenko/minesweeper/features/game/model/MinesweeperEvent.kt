package ru.roman.ishchenko.minesweeper.features.game.model

import ru.roman.ishchenko.minesweeper.domain.Cell

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:47
 */

internal sealed class MinesweeperEvent

internal object NewGameEvent: MinesweeperEvent()

internal class OpenCellEvent(x: Int, y: Int): MinesweeperEvent()

internal class FlagCellEvent(x: Int, y: Int): MinesweeperEvent()

internal class ChaneBoardEvent(board: Array<Array<Cell>>): MinesweeperEvent()

internal class WinGameEvent(timeLeft: Int): MinesweeperEvent()

internal class LoseGameEvent(timeLeft: Int): MinesweeperEvent()

internal class TimerEvent(timeLeft: Int): MinesweeperEvent()
