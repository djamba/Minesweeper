package ru.roman.ishchenko.minesweeper.features.game.model

/**
 * User: roman
 * Date: 04.01.2022
 * Time: 20:16
 */

internal sealed class CellSate {

    object OpenMineBlast: CellSate()

    object OpenMineFound: CellSate()

    object OpenMineNotFound: CellSate()

    object OpenFree: CellSate()

    class OpenNumber(val num: Int): CellSate()

    object Close: CellSate()

    object Flag: CellSate()
}
