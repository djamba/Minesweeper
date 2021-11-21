package ru.roman.ishchenko.minesweeper.features.game.model

import ru.roman.ishchenko.minesweeper.domain.Cell

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:47
 */

internal sealed class MinesweeperEvent

internal object NewGameEvent: MinesweeperEvent()

internal class OpenCellEvent(val x: Int, val y: Int): MinesweeperEvent()

internal class FlagCellEvent(val x: Int, val y: Int): MinesweeperEvent()

internal class ChaneBoardEvent(val board: Array<Array<Cell>>): MinesweeperEvent()

internal class WinGameEvent(val minFound: Int, val timeLeft: Int): MinesweeperEvent()

internal class LoseGameEvent(val minFound: Int, val timeLeft: Int): MinesweeperEvent()

internal class TimerEvent(val timeLeft: Int): MinesweeperEvent()
