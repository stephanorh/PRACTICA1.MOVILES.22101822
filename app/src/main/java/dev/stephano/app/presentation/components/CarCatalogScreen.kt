
package dev.stephano.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material3.ExperimentalMaterial3Api

data class Car(
    val brand: String,
    val model: String,
    val price: Double,
    val imageUrl: String
)

val carList = listOf(
    Car("Ferrari", "SF90 Stradale", 507000.0, "https://euroshop.com.pe/wp-content/uploads/2024/04/screen-post-hbh4GV2_6H8-unsplash-scaled-e1712986047289.jpg"),
    Car("Lamborghini", "Aventador SVJ", 517770.0, "https://euroshop.com.pe/wp-content/uploads/2024/04/pexels-victoria-ouarets-5288727-scaled-e1712844368531-768x863.jpg"),
    Car("Porsche", "911 GT3 RS", 241300.0, "https://i.pinimg.com/736x/dc/f1/6d/dcf16d578c420dc0faea58c107cd8cb3.jpg"),
    Car("Bugatti", "Chiron Super Sport 300+", 3900000.0, "https://i.blogs.es/6e2c13/bugatti-chiron-hermes-edition-01/450_1000.jpg"),
    Car("McLaren", "720S", 300000.0, "https://fotografias-2.larazon.es/clipping/cmsimages01/2021/05/20/927620C7-C6A0-41EE-ADF2-9374EA40B8D3/98.jpg?crop=1244,700,x0,y0&width=1900&height=1069&optimize=low&format=webply")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarCatalogScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Autos Deportivos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = paddingValues
        ) {
            items(carList) { car ->
                CarCard(car = car)
            }
            item {
                val totalCost = carList.sumOf { it.price }
                Text(
                    text = "Costo total: $%.2f".format(totalCost),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun CarCard(car: Car) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(car.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "${car.brand} ${car.model}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Marca: ${car.brand}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Modelo: ${car.model}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Precio: $%.2f".format(car.price),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarCatalogScreenPreview() {
    CarCatalogScreen(navController = rememberNavController())
}
