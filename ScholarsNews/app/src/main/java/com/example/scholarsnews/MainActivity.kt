package com.example.scholarsnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.scholarsnews.ui.MainScreen
import com.example.scholarsnews.ui.theme.ScholarsNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Projenin teması (ScholarsnewsTheme vb. yazabilir, ona dokunma)
            com.example.scholarsnews.ui.theme.ScholarsNewsTheme {
                MainScreen() // Uygulama açılır açılmaz bizim iskeletimiz çalışacak
            }
        }
    }
}
