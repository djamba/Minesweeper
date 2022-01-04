package ru.roman.ishchenko.minesweeper.features.game.model

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:52
 */

internal sealed class MinesweeperAction

internal object NewGameAction: MinesweeperAction()

internal class OpenCellAction(val x: Int, val y: Int): MinesweeperAction()

internal class FlagCellAction(val x: Int, val y: Int): MinesweeperAction()

internal object LoadGameAction: MinesweeperAction()

internal object SaveGameAction: MinesweeperAction()
