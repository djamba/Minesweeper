package ru.roman.ishchenko.minesweeper.features.game.model

import ru.roman.ishchenko.minesweeper.domain.Cell

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:57
 */

internal sealed class MinesweeperState

internal object StartGameState: MinesweeperState()

internal class GameSate(
    board: Array<Array<Cell>>,
    mineFlagged: Int,
    timeLeft: Int
): MinesweeperState()

internal class WinGameState(
    minFound: Int,
    timeLeft: Int
): MinesweeperState()

internal class LoseGameState(
    minFound: Int,
    timeLeft: Int
): MinesweeperState()
