
package dev.stephano.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun DrawerScaffold() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}
