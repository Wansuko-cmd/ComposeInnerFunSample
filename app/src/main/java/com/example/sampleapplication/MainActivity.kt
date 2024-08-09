package com.example.sampleapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.sampleapplication.ui.theme.SampleApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleApplicationTheme {
                Scaffold { innerPadding ->
                    var text by remember { mutableStateOf("") }
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                        )
                        SampleList(text)
                    }
                }
            }
        }
    }
}

@Composable
fun SampleList(text: String) {
    @Composable
    fun SampleItem1() {
        Log.d("SampleItem1", "Recomposed!")
        Text(text = "SampleItem1")
    }
    SampleItem1()
    SampleItem2()

    // SampleListのRecomposeを起こすため
    Text(text = text)
}

@Composable
fun SampleItem2() {
    Log.d("SampleItem2", "Recomposed!")
    Text(text = "SampleItem2")
}
