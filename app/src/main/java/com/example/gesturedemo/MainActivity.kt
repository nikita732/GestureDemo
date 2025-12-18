package com.example.gesturedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt
import com.example.gesturedemo.ui.theme.GestureDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestureDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    DragDemo(modifier)
}

@Composable
fun TapPressDemo(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("Waiting ....") }

    val tapHandler = { status: String ->
        textState = status
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(100.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { tapHandler("onPress Detected") },
                        onTap = { tapHandler("onTap Detected") },
                        onDoubleTap = { tapHandler("onDoubleTap Detected") },
                        onLongPress = { tapHandler("onLongPress Detected") }
                    )
                }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = textState,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DragDemo(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        var xOffset by remember { mutableStateOf(0f) }

        Box(
            modifier = Modifier
                .offset { IntOffset(xOffset.roundToInt(), 0) }
                .size(100.dp)
                .background(Color.Blue)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { distance ->
                        xOffset += distance
                    }
                )
        )
    }
}

@Composable
fun MultiDragDemo(modifier: Modifier = Modifier) {
    val boxSize = 100.dp
    val boxSizePx = with(LocalDensity.current) { boxSize.toPx() }

    Box(modifier = modifier.fillMaxSize()) {
        var offset by remember { mutableStateOf(Offset.Zero) }

        Box(
            modifier = Modifier
                .size(boxSize)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .background(Color.Red)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        val newX = offset.x + dragAmount.x
                        val newY = offset.y + dragAmount.y

                        val maxX = size.width - boxSizePx
                        val maxY = size.height - boxSizePx

                        offset = Offset(
                            x = newX.coerceIn(0f, maxX),
                            y = newY.coerceIn(0f, maxY)
                        )
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TapPressDemoPreview() {
    GestureDemoTheme {
        TapPressDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun DragDemoPreview() {
    GestureDemoTheme {
        DragDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun MultiDragDemoPreview() {
    GestureDemoTheme {
        MultiDragDemo()
    }
}