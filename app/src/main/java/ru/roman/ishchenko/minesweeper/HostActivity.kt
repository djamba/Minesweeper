package ru.roman.ishchenko.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.roman.ishchenko.minesweeper.features.game.model.CellSate
import ru.roman.ishchenko.minesweeper.features.game.model.GameSate
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameEvent
import ru.roman.ishchenko.minesweeper.features.game.model.OpenCellAction
import ru.roman.ishchenko.minesweeper.features.game.presentation.MinesweeperWorkflow
import ru.roman.ishchenko.minesweeper.ui.theme.MinesweeperTheme

@AndroidEntryPoint
class HostActivity : ComponentActivity() {

    private val minesweeperWorkflow: MinesweeperWorkflow by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewState by minesweeperWorkflow.viewState.collectAsState()
            minesweeperWorkflow.obtainEvent(NewGameEvent)

            MinesweeperTheme {
                Surface(color = MaterialTheme.colors.background) {
                    when (viewState) {
                        is GameSate -> Board(viewState as GameSate)
                        else -> Unit
                    }
                }
            }
        }
    }


    @Composable
    private fun Board(gameViewState: GameSate) {
        val board = gameViewState.board
        Column {
            for (i in board.indices) {
                Row(Modifier.padding(2.dp)) {
                    for (j in board[i].indices) {
                        Cell(i, j, board[i][j])
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
    }

    @Composable
    private fun Cell(x: Int, y: Int, cellSate: CellSate) {
        val scope = rememberCoroutineScope()
        Card(modifier = Modifier
            .size(30.dp, 30.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(
                width = 1.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                scope.launch {
                    minesweeperWorkflow.handleAction(OpenCellAction(x, y))
                }
            }

        ) {
            val state = when (cellSate) {
                is CellSate.OpenMineBlast -> "X"
                is CellSate.OpenMineFound -> "V"
                is CellSate.OpenMineNotFound -> "N"
                is CellSate.OpenFree -> " "
                is CellSate.OpenNumber -> "${cellSate.num}"
                is CellSate.Close -> "C"
                is CellSate.Flag -> "F"
            }
            Text(
                text = state,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        val board = mutableListOf<MutableList<CellSate>>()
        for (i in 0..10) {
            board.add(mutableListOf())
            for (j in 0..10) {
                board[i].add(CellSate.Close)
            }
        }
        MinesweeperTheme {
            Board(GameSate(board, 1, 1))
        }
    }
}
