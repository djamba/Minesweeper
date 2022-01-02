package ru.roman.ishchenko.minesweeper.domain

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:27
 */

internal class Cell {
    val state: Int = 0 // OPEN, CLOSE, FLAG
    val hasMine: Boolean = false
    val nearbyMinesCount: Int = 0

    fun open() {

    }

    fun flag() {

    }

    fun setNearbyMines(count: Int) {

    }
}
