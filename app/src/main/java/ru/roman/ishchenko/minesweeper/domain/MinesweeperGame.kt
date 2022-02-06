package ru.roman.ishchenko.minesweeper.domain

/**
 * User: roman
 * Date: 04.01.2022
 * Time: 20:24
 */

internal class MinesweeperGame {
    private var board: Board? = null
    private var flagUsed: Int = 0
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
        return if (gameStarted && currentBoard != null) {
            val cell = currentBoard.openCell(x, y)
            if (cell.state == CellState.BLAST) {
                gameStarted = false
                return GameResult.Lose(currentBoard, foundMines)
            }
            if (checkWinGame()) {
                gameStarted = false
                return GameResult.Win(currentBoard, foundMines)
            }
            GameResult.Continue(currentBoard)
        } else {
            GameResult.ErrorGameNotStarted
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

    fun flagCell(x: Int, y: Int): GameResult {
        val currentBoard = board
        return if (currentBoard != null) {
            if (currentBoard.board[x][y].state == CellState.CLOSE) {
                val hasMine = currentBoard.flagCell(x, y)
                if (hasMine) foundMines++
                flagUsed++
            } else if (currentBoard.board[x][y].state == CellState.FLAG) {
                val hasMine = currentBoard.flagCell(x, y)
                if (hasMine) foundMines--
                flagUsed--
            }
            GameResult.Continue(currentBoard)
        } else {
            GameResult.ErrorGameNotStarted
        }
    }

    sealed class GameResult {
        class Continue(val board: Board): GameResult()
        class Win(val board: Board, val foundMines: Int): GameResult()
        class Lose(val board: Board, val foundMines: Int): GameResult()
        object ErrorGameNotStarted: GameResult()
    }
}
