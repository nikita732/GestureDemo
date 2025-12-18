package com.example.gesturedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
    AdvancedMultiTouchDemo(modifier)
}

@Composable
fun MultiTouchDemo(modifier: Modifier = Modifier) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotate ->
                        scale *= zoom
                        rotation += rotate
                        offset += pan
                    }
                }
                .background(Color.Blue)
                .size(100.dp)
        )
    }
}

@Composable
fun AdvancedMultiTouchDemo(modifier: Modifier = Modifier) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        rotationZ = rotation,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, rotate ->
                            scale *= zoom
                            rotation += rotate
                            offset += pan
                        }
                    }
                    .background(Color.Red)
                    .size(150.dp)
            )
        }
    }
}

@Composable
fun TestAllGestures(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var tapText by remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapText = "" },
                        onDoubleTap = { tapText = "" },
                        onLongPress = { tapText = "" }
                    )
                }
        )

        var dragOffset by remember { mutableStateOf(Offset.Zero) }
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(dragOffset.x.roundToInt(), dragOffset.y.roundToInt()) }
                .background(Color.Green)
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        dragOffset += dragAmount
                    }
                }
        )
    }
}

@Composable
fun TapPressDemo(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }

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
                        onPress = {  },
                        onTap = {  },
                        onDoubleTap = {  },
                        onLongPress = {  }
                    )
                }
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
        )
    }
}

@Composable
fun PointerInputDrag(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        var xOffset by remember { mutableStateOf(0f) }
        var yOffset by remember { mutableStateOf(0f) }

        Box(
            modifier = Modifier
                .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
                .background(Color.Blue)
                .size(100.dp)
                .pointerInput(Unit) {
                    detectDragGestures { _, distance ->
                        xOffset += distance.x
                        yOffset += distance.y
                    }
                }
        )
    }
}

@Composable
fun ScrollModifiers(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(150.dp)
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .size(360.dp, 270.dp)
                .background(Color.Blue)
        )
    }
}
