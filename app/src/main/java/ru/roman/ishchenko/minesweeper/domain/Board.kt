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

    fun openCell(x: Int, y: Int): Cell {
        if (board[x][y].state != CellState.CLOSE) {
            return board[x][y]
        }

        board[x][y].open()
        if (board[x][y].state != CellState.BLAST) {
            openArea(x, y)
        } else {
            openMines()
        }

        return board[x][y]
    }

    fun flagCell(x: Int, y: Int) =
        board[x][y].flag()

    private fun openArea(x: Int, y: Int) {
        if (x > 0 && board[x-1][y].hasMine) board[x][y].nearbyMinesCount++
        if (y > 0 && board[x][y-1].hasMine) board[x][y].nearbyMinesCount++
        if (x < board.size-1 && board[x+1][y].hasMine) board[x][y].nearbyMinesCount++
        if (y < board[0].size-1 && board[x][y+1].hasMine) board[x][y].nearbyMinesCount++

        if (x > 0 && y > 0 && board[x-1][y-1].hasMine) board[x][y].nearbyMinesCount++
        if (x > 0 && y < board[0].size-1 && board[x-1][y+1].hasMine) board[x][y].nearbyMinesCount++
        if (x < board.size-1 && y > 0 && board[x+1][y-1].hasMine) board[x][y].nearbyMinesCount++
        if (x < board.size-1 && y < board[0].size-1 && board[x+1][y+1].hasMine) board[x][y].nearbyMinesCount++

        board[x][y].open()
        if (board[x][y].nearbyMinesCount > 0) return

        if (x > 0 && board[x-1][y].state == CellState.CLOSE) openArea(x-1, y)
        if (y > 0 && board[x][y-1].state == CellState.CLOSE) openArea(x, y-1)
        if (x < board.size-1 && board[x+1][y].state == CellState.CLOSE) openArea(x+1, y)
        if (y < board[0].size-1 && board[x][y+1].state == CellState.CLOSE) openArea(x, y+1)
    }

    private fun openMines() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j].openMine()
            }
        }
    }
}
