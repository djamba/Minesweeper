package ru.roman.ishchenko.minesweeper.domain

import org.junit.Test
import org.junit.Assert.*

private const val BOARD_SIZE_X = 10
private const val BOARD_SIZE_Y = 10
private const val BOARD_MINE_COUNT = 3

class MinesweeperGameTest {

    private val minesweeperGame = MinesweeperGame()

    @Test
    fun `WHEN new game init THEN parameters are as specified`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        assertEquals(BOARD_SIZE_X, board.board.size)
        assertEquals(BOARD_SIZE_Y, board.board[0].size)
        assertEquals(BOARD_MINE_COUNT, board.board.sumOf { it.count { cell -> cell.hasMine } })
    }

    @Test
    fun `WHEN open empty cell THEN cell open and game continue`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        val (x, y) = board.findCell { it.hasMine.not() }
        val result = minesweeperGame.openCell(x, y)

        assertTrue(result is MinesweeperGame.GameResult.Continue)
        assertEquals(CellState.OPEN, board.board[x][y].state)
    }

    @Test
    fun `WHEN open mined cell THEN cell blast and game over`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        val (x, y) = board.findCell { it.hasMine }
        val result = minesweeperGame.openCell(x, y)

        assertTrue(result is MinesweeperGame.GameResult.Lose)
        assertEquals(CellState.BLAST, board.board[x][y].state)
    }

    @Test
    fun `WHEN open all empty cell THEN win game`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, mineCount = 1)

        var result: MinesweeperGame.GameResult? = null
        for (x in board.board.indices) {
            for (y in board.board[x].indices) {
                if (board.board[x][y].hasMine.not() && board.board[x][y].state == CellState.CLOSE) {
                    result = minesweeperGame.openCell(x, y)
                }
            }
        }

        assertTrue(result is MinesweeperGame.GameResult.Win)
    }

    @Test
    fun `WHEN open cell and game finish THEN game error`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        val (x, y) = board.findCell { it.hasMine }
        minesweeperGame.openCell(x, y)

        val result = minesweeperGame.openCell(1, 1)

        assertTrue(result is MinesweeperGame.GameResult.ErrorGameNotStarted)
    }

    @Test
    fun `WHEN flag closed cell THEN cell is flagged`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        minesweeperGame.flagCell(1, 1)

        assertEquals(CellState.FLAG, board.board[1][1].state)
    }

    @Test
    fun `WHEN flag flagged cell THEN cell is close`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        minesweeperGame.flagCell(1, 1)
        minesweeperGame.flagCell(1, 1)

        assertEquals(CellState.CLOSE, board.board[1][1].state)
    }

    @Test
    fun `WHEN flag cell and game finish THEN game error`() {
        val board = minesweeperGame.newGame(BOARD_SIZE_X, BOARD_SIZE_Y, BOARD_MINE_COUNT)

        val (x, y) = board.findCell { it.hasMine }
        minesweeperGame.openCell(x, y)

        val result = minesweeperGame.flagCell(1, 1)

        assertTrue(result is MinesweeperGame.GameResult.ErrorGameNotStarted)
    }

    private fun Board.findCell(predicate: (c: Cell) -> Boolean): Pair<Int, Int> {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (predicate(board[i][j])) {
                    return i to j
                }
            }
        }
        throw IllegalStateException("Cell not found")
    }
}
