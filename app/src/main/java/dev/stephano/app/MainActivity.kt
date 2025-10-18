
package dev.stephano.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.stephano.app.presentation.navigation.DrawerScaffold
import dev.stephano.app.ui.theme.PRACTICA1MOVILES22101822Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRACTICA1MOVILES22101822Theme {
                DrawerScaffold()
            }
        }
    }
}
