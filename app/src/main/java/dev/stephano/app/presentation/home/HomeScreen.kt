
package dev.stephano.app.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.stephano.app.presentation.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menú Principal") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { navController.navigate(AppScreens.WaterConsumptionScreen.route) }) {
                Text("Calculadora de consumo de agua")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(AppScreens.PhysicalActivityScreen.route) }) {
                Text("Registro de actividad física")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(AppScreens.CarCatalogScreen.route) }) {
                Text("Catálogo de Autos deportivos")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
