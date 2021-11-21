package ru.roman.ishchenko.minesweeper.features.game.model

import ru.roman.ishchenko.minesweeper.domain.Cell

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:57
 */

internal sealed class MinesweeperState

internal object StartGameState: MinesweeperState()

internal data class GameSate(
    val board: Array<Array<Cell>>,
    val mineFlagged: Int,
    val timeLeft: Int
): MinesweeperState()

internal data class WinGameState(
    val minFound: Int,
    val timeLeft: Int
): MinesweeperState()

internal data class LoseGameState(
    val minFound: Int,
    val timeLeft: Int
): MinesweeperState()
