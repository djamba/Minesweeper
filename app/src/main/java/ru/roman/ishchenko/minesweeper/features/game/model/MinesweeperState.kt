package ru.roman.ishchenko.minesweeper.features.game.model

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:57
 */

internal sealed class MinesweeperState

internal object StartGameState: MinesweeperState()

internal data class GameSate(
    val board: List<List<CellSate>>,
    val mineFlagged: Int,
    val timeLeft: Int
): MinesweeperState()

internal data class WinGameState(
    val board: List<List<CellSate>>,
    val minFound: Int,
    val timeLeft: Int
): MinesweeperState()

internal data class LoseGameState(
    val board: List<List<CellSate>>,
    val minFound: Int,
    val timeLeft: Int
): MinesweeperState()
