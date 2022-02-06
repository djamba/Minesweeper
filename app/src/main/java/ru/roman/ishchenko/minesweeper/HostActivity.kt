package ru.roman.ishchenko.minesweeper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.roman.ishchenko.minesweeper.features.game.model.*
import ru.roman.ishchenko.minesweeper.features.game.model.CellSate
import ru.roman.ishchenko.minesweeper.features.game.model.GameSate
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameEvent
import ru.roman.ishchenko.minesweeper.features.game.model.OpenCellAction
import ru.roman.ishchenko.minesweeper.features.game.model.WinGameState
import ru.roman.ishchenko.minesweeper.features.game.presentation.MinesweeperWorkflow
import ru.roman.ishchenko.minesweeper.ui.theme.MinesweeperTheme

@ExperimentalFoundationApi
@AndroidEntryPoint
class HostActivity : ComponentActivity() {

    private val minesweeperWorkflow: MinesweeperWorkflow by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            minesweeperWorkflow.obtainEvent(NewGameEvent)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                minesweeperWorkflow.action.collect { action ->
                    val text = when (action) {
                        is WinGameAction -> "Win game!"
                        is LoseGameAction -> "Lose game :("
                        is ErrorGameAction -> "Error!"
                        else -> ""
                    }
                    Toast.makeText(this@HostActivity, text, Toast.LENGTH_LONG).show()
                }
            }
        }

        setContent {
            val viewState by minesweeperWorkflow.viewState.collectAsState()

            MinesweeperTheme {
                Surface(color = MaterialTheme.colors.background) {
                    when (viewState) {
                        is GameSate -> Board((viewState as GameSate).board)
                        is WinGameState -> Board((viewState as WinGameState).board)
                        is LoseGameState -> Board((viewState as LoseGameState).board)
                        else -> Unit
                    }
                }
            }
        }
    }

    @Composable
    private fun Board(board: List<List<CellSate>>) {
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
            .combinedClickable(
                onClick = {
                    scope.launch {
                        minesweeperWorkflow.handleAction(OpenCellAction(x, y))
                    }
                },
                onLongClick = {
                    scope.launch {
                        minesweeperWorkflow.handleAction(FlagCellAction(x, y))
                    }
                }
            )
        ) {
            var textColor = Color.Black
            val state = when (cellSate) {
                is CellSate.OpenMineBlast -> "X"
                is CellSate.OpenMineFound -> "V"
                is CellSate.OpenMineNotFound -> "N"
                is CellSate.OpenFree -> " "
                is CellSate.OpenNumber -> {
                    textColor = getNumTextColor(cellSate.num)
                    "${cellSate.num}"
                }
                is CellSate.Close -> "C"
                is CellSate.Flag -> "F"
            }
            Text(
                text = state,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
            )
        }
    }

    private fun getNumTextColor(num: Int) =
        when (num) {
            1 -> Color.Green
            2 -> Color.Blue
            3 -> Color.Magenta
            4 -> Color.Red
            else -> Color.Black
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
            Board(board)
        }
    }
}
