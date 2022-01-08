package ru.roman.ishchenko.minesweeper.domain

import java.lang.IllegalStateException

/**
 * User: roman
 * Date: 04.01.2022
 * Time: 20:24
 */

internal class MinesweeperGame {
    private var board: Board? = null
    private var flagUsed: Int = 0
    private var timeLeft: Int = 0
    private var laidMines: Int = 0
    private var foundMines: Int = 0
    private var gameStarted: Boolean = false

    fun newGame(sizeX: Int, sizeY: Int, mineCount: Int): Board {
        val board = Board(sizeX, sizeY, mineCount)
        this.board = board
        this.laidMines = mineCount
        this.gameStarted = true
        return board
    }

    fun openCell(x: Int, y: Int): GameResult {
        val currentBoard = board
        if (gameStarted && currentBoard != null) {
            val cell = currentBoard.openCell(x, y)
            if (cell.state == CellState.BLAST) {
                gameStarted = false
                return GameResult.Lose(currentBoard, foundMines)
            }
            if (checkWinGame()) {
                gameStarted = false
                return GameResult.Win(currentBoard, foundMines)
            }
            return GameResult.Continue(currentBoard)
        } else {
            throw IllegalStateException("Game not started")
        }
    }

    private fun checkWinGame(): Boolean {
        val board = board?.board ?: return false
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j].state == CellState.BLAST ||
                    board[i][j].state == CellState.CLOSE
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun flagCell(x: Int, y: Int): Board {
        val currentBoard = board
        if (currentBoard != null) {
            if (currentBoard.board[x][y].state == CellState.CLOSE) {
                val hasMine = currentBoard.flagCell(x, y)
                if (hasMine) foundMines++
                flagUsed++
            } else if (currentBoard.board[x][y].state == CellState.FLAG) {
                val hasMine = currentBoard.flagCell(x, y)
                if (hasMine) foundMines--
                flagUsed--
            }
            return currentBoard
        } else {
            throw IllegalStateException("Game not started")
        }
    }

    fun setTimeLeft(timeLeft: Int) {
        this.timeLeft = timeLeft
    }

    sealed class GameResult(open val board: Board) {
        class Continue(override val board: Board): GameResult(board)
        class Win(override val board: Board, val foundMines: Int): GameResult(board)
        class Lose(override val board: Board, val foundMines: Int): GameResult(board)
    }
}
