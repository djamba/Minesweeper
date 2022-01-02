package ru.roman.ishchenko.minesweeper

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import ru.roman.ishchenko.minesweeper.features.game.model.GameSate
import ru.roman.ishchenko.minesweeper.features.game.model.NewGameEvent
import ru.roman.ishchenko.minesweeper.features.game.presentation.MinesweeperWorkflow
import ru.roman.ishchenko.minesweeper.ui.theme.MinesweeperTheme

@AndroidEntryPoint
class HostActivity : ComponentActivity() {

    private val minesweeperWorkflow: MinesweeperWorkflow by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Board(minesweeperWorkflow)
                }
            }
        }
    }
}

@Composable
internal fun Board(minesweeperWorkflow: MinesweeperWorkflow) {

    val viewState by minesweeperWorkflow.viewState.collectAsState()
    minesweeperWorkflow.obtainEvent(NewGameEvent)

    if (viewState is GameSate) {
        val board = (viewState as GameSate).board
        Column {
            for (i in board.indices) {
                Row(Modifier.padding(2.dp)) {
                    for (j in board[i].indices) {
                        Cell(name = (i + j).toString())
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(name: String) {
    val context = LocalContext.current
    Card(//shape = RoundedCornerShape(5.dp),
        //border = BorderStroke(width = 1.dp, color = Color.Blue),
        modifier = Modifier
            .size(30.dp, 30.dp)
            .clip(RoundedCornerShape(5.dp))
            //.background(Color.LightGray)
            .border(
                width = 1.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                Toast.makeText(context, "$name", Toast.LENGTH_LONG).show()
            }

    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp)
        )
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MinesweeperTheme {
        Board()
    }
}*/