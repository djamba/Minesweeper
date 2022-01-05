package ru.roman.ishchenko.minesweeper.features.game.middleware

import ru.roman.ishchenko.minesweeper.domain.Cell
import ru.roman.ishchenko.minesweeper.domain.CellState
import ru.roman.ishchenko.minesweeper.domain.MinesweeperGame
import ru.roman.ishchenko.minesweeper.features.base.Middleware
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.FlagCellAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperAction
import ru.roman.ishchenko.minesweeper.features.game.model.MinesweeperEvent
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameAction
import ru.roman.ishchenko.minesweeper.features.game.model.OpenCellAction
import javax.inject.Inject

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:43
 */

internal class GameMiddleware @Inject constructor(
    private val minesweeperGame: MinesweeperGame
): Middleware<MinesweeperEvent, MinesweeperAction> {

    suspend fun handleAction(action: NewGameAction): MinesweeperEvent {
        val settings = action.gameSettings
        val board = minesweeperGame.newGame(settings.sizeX, settings.sizeY, settings.mineCount).board
        val boardState = createBoardState(board)
        return ChaneBoardEvent(boardState)
    }

    suspend fun handleAction(action: OpenCellAction): MinesweeperEvent {
        val board = minesweeperGame.openCell(action.x, action.y).board
        val boardState = createBoardState(board)
        return ChaneBoardEvent(boardState)
    }

    suspend fun handleAction(action: FlagCellAction): MinesweeperEvent {
        val board = minesweeperGame.flagCell(action.x, action.y).board
        val boardState = createBoardState(board)
        return ChaneBoardEvent(boardState, 1)
    }

    private fun createBoardState(board: Array<Array<Cell>>): List<List<CellSate>> {
        val boardState = mutableListOf<MutableList<CellSate>>()
        for (i in board.indices) {
            boardState.add(mutableListOf())
            for (j in board[i].indices) {
                val cell = board[i][j]
                val cellState = when {
                    cell.state == CellState.OPEN && cell.hasMine -> CellSate.OpenMineBlast
                    cell.state == CellState.OPEN && cell.hasMine.not() && cell.nearbyMinesCount == 0 -> CellSate.OpenFree
                    cell.state == CellState.OPEN && cell.hasMine.not() && cell.nearbyMinesCount > 0 -> {
                        CellSate.OpenNumber(cell.nearbyMinesCount)
                    }
                    cell.state == CellState.FLAG -> CellSate.Flag
                    cell.state == CellState.BLAST -> CellSate.OpenMineBlast
                    else -> CellSate.Close
                }
                boardState[i].add(cellState)
            }
        }
        return boardState
    }
}
