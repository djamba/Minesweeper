package ru.roman.ishchenko.minesweeper.features.game.model

import ru.roman.ishchenko.minesweeper.features.game.settings.MinesweeperSettings

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:52
 */

internal sealed class MinesweeperAction

internal class NewGameAction(val gameSettings: MinesweeperSettings): MinesweeperAction()

internal class OpenCellAction(val x: Int, val y: Int): MinesweeperAction()

internal class FlagCellAction(val x: Int, val y: Int): MinesweeperAction()

internal object LoadGameAction: MinesweeperAction()

internal object SaveGameAction: MinesweeperAction()

internal object WinGameAction: MinesweeperAction()

internal object LoseGameAction: MinesweeperAction()
