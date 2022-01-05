package ru.roman.ishchenko.minesweeper.features.game.settings

/**
 * User: roman
 * Date: 05.01.2022
 * Time: 18:34
 */

internal sealed class MinesweeperSettings(val sizeX: Int, val sizeY: Int, val mineCount: Int)

internal object EasySettings: MinesweeperSettings(10, 10, 5)

internal object MediumSettings: MinesweeperSettings(10, 10, 10)

internal object HardSettings: MinesweeperSettings(10, 10, 15)
