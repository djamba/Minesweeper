package ru.roman.ishchenko.minesweeper.domain

import java.lang.IllegalStateException

/**
 * User: roman
 * Date: 04.01.2022
 * Time: 20:24
 */

internal class MinesweeperGame(
    private val sizeX: Int,
    private val sizeY: Int,
    private val mineCount: Int
) {
    private var board: Board? = null
    private var flagUsed: Int = 0
    private var timeLeft: Int = 0

    fun newGame(): Board {
        val board = Board(sizeX, sizeY, mineCount)
        this.board = board
        return board
    }

    fun openCell(x: Int, y: Int): Board {
        val currentBoard = board
        if (currentBoard != null) {
            currentBoard.openCell(x, y)
            return currentBoard
        } else {
            throw IllegalStateException("Game not started")
        }
    }

    fun flagCell(x: Int, y: Int): Board {
        val currentBoard = board
        if (currentBoard != null) {
            currentBoard.flagCell(x, y)
            return currentBoard
        } else {
            throw IllegalStateException("Game not started")
        }
    }

    fun setTimeLeft(timeLeft: Int) {
        this.timeLeft = timeLeft
    }

    fun incUsedFlag() {
        flagUsed++
    }

    fun decUsedFlag() {
        flagUsed--
    }
}
