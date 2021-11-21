package ru.roman.ishchenko.minesweeper.features.base

/**
 * User: roman
 * Date: 19.11.2021
 * Time: 17:13
 */

interface Reducer<S, E, A> {
    fun reduce(state: S, event: E): Pair<S?, A?>
}
