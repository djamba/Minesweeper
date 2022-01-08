package ru.roman.ishchenko.minesweeper.domain

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 22:27
 */

internal enum class CellState { OPEN, CLOSE, FLAG, BLAST }

internal class Cell {
    var state: CellState = CellState.CLOSE
        private set
    var hasMine: Boolean = false
    var nearbyMinesCount: Int = 0

    fun open() {
        when {
            state == CellState.CLOSE && hasMine.not() -> {
                state = CellState.OPEN
            }
            state == CellState.CLOSE && hasMine -> {
                state = CellState.BLAST
            }
        }
    }

    fun openMine() {
        if (hasMine && state == CellState.CLOSE) {
            state = CellState.OPEN
        }
    }

    fun flag(): Boolean {
        state = if (state == CellState.CLOSE) {
            CellState.FLAG
        } else {
            CellState.CLOSE
        }
        return hasMine
    }
}
