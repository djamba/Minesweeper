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
        var mine = mineCount
        loop@for (i in board.indices) {
            for (j in board.indices) {
                board[i][j].hasMine = true
                val x = Random.nextInt(1, sizeX - 1)
                val y = Random.nextInt(1, sizeY - 1)

                val tmp = board[x][y]
                board[x][y] = board[i][j]
                board[i][j] = tmp

                mine--
                if (mine == 0) break@loop
            }
        }
    }

    fun openCell(x: Int, y: Int) {
        board[x][y].open()
    }

    fun flagCell(x: Int, y: Int) {
        board[x][y].flag()
    }
}
