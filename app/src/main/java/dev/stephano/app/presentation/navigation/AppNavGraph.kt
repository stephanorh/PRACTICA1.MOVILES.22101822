
package dev.stephano.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.stephano.app.presentation.components.CarCatalogScreen
import dev.stephano.app.presentation.components.PhysicalActivityScreen
import dev.stephano.app.presentation.components.WaterConsumptionScreen
import dev.stephano.app.presentation.home.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier
    ) {
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreens.WaterConsumptionScreen.route) {
            WaterConsumptionScreen(navController = navController)
        }
        composable(AppScreens.PhysicalActivityScreen.route) {
            PhysicalActivityScreen(navController = navController)
        }
        composable(AppScreens.CarCatalogScreen.route) {
            CarCatalogScreen(navController = navController)
        }
    }
}
