package ru.roman.ishchenko.minesweeper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roman.ishchenko.minesweeper.domain.MinesweeperGame
import javax.inject.Singleton

/**
 * User: roman
 * Date: 04.01.2022
 * Time: 20:55
 */

@Module
@InstallIn(SingletonComponent::class)
internal class GameModule {

    @Singleton
    @Provides
    fun provideHttpService(): MinesweeperGame {
        return MinesweeperGame()
    }
}
