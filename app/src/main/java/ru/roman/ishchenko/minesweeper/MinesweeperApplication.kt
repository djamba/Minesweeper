package ru.roman.ishchenko.minesweeper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * User: roman
 * Date: 26.12.2021
 * Time: 23:46
 */

@HiltAndroidApp
class MinesweeperApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
