
package dev.stephano.app.presentation.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object WaterConsumptionScreen : AppScreens("water_consumption_screen")
    object PhysicalActivityScreen : AppScreens("physical_activity_screen")
    object CarCatalogScreen : AppScreens("car_catalog_screen")
}
