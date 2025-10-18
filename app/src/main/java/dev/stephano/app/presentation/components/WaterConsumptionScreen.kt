
package dev.stephano.app.presentation.components

import androidx.compose.foundation.clickable
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
fun WaterConsumptionScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Masculino") }
    var result by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Consumo de Agua") },
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
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la persona") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Peso corporal (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            GenderSelector(selectedGender = gender, onGenderSelected = { gender = it })

            Button(
                onClick = {
                    val weightValue = weight.toDoubleOrNull()
                    if (name.isBlank() || weight.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                        }
                    } else if (weightValue == null || weightValue !in 5.0..200.0) {
                        scope.launch {
                            snackbarHostState.showSnackbar("El peso debe ser un número positivo entre 5 y 200.")
                        }
                    } else {
                        val genderFactor = when (gender) {
                            "Masculino" -> 1.02
                            "Femenino" -> 1.01
                            else -> 1.00
                        }
                        val recommendedWater = weightValue * 0.035 * genderFactor
                        result = "%s debe beber aproximadamente %.2f litros de agua al día".format(name, recommendedWater)
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

@Composable
fun GenderSelector(selectedGender: String, onGenderSelected: (String) -> Unit) {
    val genders = listOf("Masculino", "Femenino", "Sin especificar")
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Género:", modifier = Modifier.padding(bottom = 4.dp))
        genders.forEach { gender ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onGenderSelected(gender) }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedGender == gender,
                    onClick = { onGenderSelected(gender) }
                )
                Text(text = gender, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterConsumptionScreenPreview() {
    WaterConsumptionScreen(navController = rememberNavController())
}
