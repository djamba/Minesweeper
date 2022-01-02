package ru.roman.ishchenko.minesweeper.domain

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:27
 */

internal class Board(
    sizeX: Int,
    sizeY: Int
) {
    val bard = Array<Array<Cell>>(sizeX) { Array<Cell>(sizeY) { Cell() } }

    fun newGame() {

    }

    fun openCell() {

    }

    fun flagCell() {

    }
}
