package com.example.gesturedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.gestures.rememberTransformableState
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
    // TapPressDemo(modifier)
    // DragDemo(modifier)
    // MultiDragDemo(modifier)
    // PointerInputDrag(modifier)
    // PointerInputDragWithInfo(modifier)
    // ScrollableModifier(modifier)
    // ScrollModifiers(modifier)
    MultiTouchDemo(modifier) // Демо масштабирования
}

@Composable
fun MultiTouchDemo(modifier: Modifier = Modifier) {
    var scale by remember { mutableStateOf(1f) }

    val state = rememberTransformableState { scaleChange, offsetChange, rotationChange ->
        scale *= scaleChange
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
                .transformable(state = state)
                .background(Color.Blue)
                .size(100.dp)
        )
    }
}

@Composable
fun AdvancedTransformableDemo(modifier: Modifier = Modifier) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { scaleChange, offsetChange, rotationChange ->
        scale *= scaleChange
        rotation += rotationChange
        offset += offsetChange
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Масштаб: ${String.format("%.2f", scale)}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Поворот: ${String.format("%.1f", rotation)}°",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
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
                    .transformable(state = state)
                    .background(Color.Green)
                    .size(100.dp)
            )
        }
    }
}

@Composable
fun ImageTransformableDemo(modifier: Modifier = Modifier) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { scaleChange, offsetChange, rotationChange ->
        scale *= scaleChange
        rotation += rotationChange
        offset += offsetChange
    }

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
                .transformable(state = state)
                .background(Color.Red)
                .size(200.dp)
        ) {
            Text(
                text = "Масштабируйте и вращайте",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Информационная панель
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            Text(
                text = "Масштаб: ${String.format("%.2f", scale)}",
                color = Color.White
            )
            Text(
                text = "Поворот: ${String.format("%.1f", rotation)}°",
                color = Color.White
            )
            Text(
                text = "Сдвиг: (${offset.x.roundToInt()}, ${offset.y.roundToInt()})",
                color = Color.White
            )
        }
    }
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
fun PointerInputDragWithInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var xOffset by remember { mutableStateOf(0f) }
        var yOffset by remember { mutableStateOf(0f) }

        Text(
            text = "X: ${xOffset.roundToInt()}, Y: ${yOffset.roundToInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
                .background(Color.Green)
                .size(100.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, distance ->
                        xOffset += distance.x
                        yOffset += distance.y
                    }
                }
        )

        Text(
            text = "Перетаскивайте блок в любом направлении",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun ScrollableModifier(modifier: Modifier = Modifier) {
    var offset by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { distance ->
                    offset += distance
                    distance
                }
            )
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .offset { IntOffset(0, offset.roundToInt()) }
                .background(Color.Red)
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
            .background(Color.LightGray)
    ) {
        Canvas(
            modifier = Modifier
                .size(360.dp, 270.dp)
        ) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(0f, 0f),
                size = size
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiTouchDemoPreview() {
    GestureDemoTheme {
        MultiTouchDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedTransformableDemoPreview() {
    GestureDemoTheme {
        AdvancedTransformableDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun ImageTransformableDemoPreview() {
    GestureDemoTheme {
        ImageTransformableDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollModifiersPreview() {
    GestureDemoTheme {
        ScrollModifiers()
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableModifierPreview() {
    GestureDemoTheme {
        ScrollableModifier()
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

@Preview(showBackground = true)
@Composable
fun PointerInputDragPreview() {
    GestureDemoTheme {
        PointerInputDrag()
    }
}

@Preview(showBackground = true)
@Composable
fun PointerInputDragWithInfoPreview() {
    GestureDemoTheme {
        PointerInputDragWithInfo()
    }
}