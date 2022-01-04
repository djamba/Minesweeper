package ru.roman.ishchenko.minesweeper.domain

import kotlin.random.Random

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:27
 */

internal class Board(
    sizeX: Int,
    sizeY: Int,
    mineCount: Int
) {
    val board = Array<Array<Cell>>(sizeX) { Array<Cell>(sizeY) { Cell() } }

    init {
        for (i in 0..mineCount) {
            val x = Random.nextInt(1, sizeX - 1)
            val y = Random.nextInt(1, sizeY - 1)
            // TODO: check double set mine
            board[x][y].hasMine = true
        }
    }

    fun openCell(x: Int, y: Int) {
        board[x][y].open()
    }

    fun flagCell(x: Int, y: Int) {
        board[x][y].flag()
    }
}
