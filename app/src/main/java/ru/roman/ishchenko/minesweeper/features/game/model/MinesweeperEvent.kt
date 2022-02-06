package ru.roman.ishchenko.minesweeper.features.game.model

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:47
 */

internal sealed class MinesweeperEvent

internal object NewGameEvent: MinesweeperEvent()

internal class OpenCellEvent(val x: Int, val y: Int): MinesweeperEvent()

internal class FlagCellEvent(val x: Int, val y: Int): MinesweeperEvent()

internal class ChaneBoardEvent(val board: List<List<CellSate>>, val mineFlagged: Int = 0): MinesweeperEvent()

internal class WinGameEvent(val board: List<List<CellSate>>, val minFound: Int): MinesweeperEvent()

internal class LoseGameEvent(val board: List<List<CellSate>>, val minFound: Int): MinesweeperEvent()

internal object ErrorGameEvent: MinesweeperEvent()

internal class TimerEvent(val timeLeft: Int): MinesweeperEvent()

internal object SuccessSaveGameEvent: MinesweeperEvent()

internal object ErrorSaveGameEvent: MinesweeperEvent()
