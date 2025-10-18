
package dev.stephano.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityScreen(navController: NavController) {
    var activityType by remember { mutableStateOf("Correr") }
    var duration by remember { mutableStateOf("") }
    var intensity by remember { mutableStateOf("Media") }
    var result by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Registro de Actividad Física") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActivityTypeSelector(selectedActivity = activityType, onActivitySelected = { activityType = it })

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duración (minutos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            IntensitySelector(selectedIntensity = intensity, onIntensitySelected = { intensity = it })

            Button(
                onClick = {
                    val durationValue = duration.toIntOrNull()
                    if (duration.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                        }
                    } else if (durationValue == null || durationValue <= 0) {
                        scope.launch {
                            snackbarHostState.showSnackbar("La duración en minutos debe ser entera positiva.")
                        }
                    } else {
                        val caloriesPerMinute = when (activityType) {
                            "Correr" -> 10
                            "Caminar" -> 5
                            "Nadar" -> 8
                            "Ciclismo" -> 7
                            else -> 4 // Yoga
                        }
                        val intensityFactor = when (intensity) {
                            "Baja" -> 0.8
                            "Media" -> 1.0
                            else -> 1.2 // Alta
                        }
                        val caloriesBurned = caloriesPerMinute * durationValue * intensityFactor
                        result = "Calorías quemadas: %.2f".format(caloriesBurned)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            if (result.isNotEmpty()) {
                Text(
                    text = result,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTypeSelector(selectedActivity: String, onActivitySelected: (String) -> Unit) {
    val activities = listOf("Correr", "Caminar", "Nadar", "Ciclismo", "Yoga")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedActivity,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de actividad") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            activities.forEach { activity ->
                DropdownMenuItem(
                    text = { Text(activity) },
                    onClick = {
                        onActivitySelected(activity)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun IntensitySelector(selectedIntensity: String, onIntensitySelected: (String) -> Unit) {
    val intensities = listOf("Baja", "Media", "Alta")
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Intensidad:", modifier = Modifier.padding(end = 8.dp))
        intensities.forEach { intensity ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedIntensity == intensity,
                    onClick = { onIntensitySelected(intensity) }
                )
                Text(intensity)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhysicalActivityScreenPreview() {
    PhysicalActivityScreen(navController = rememberNavController())
}
