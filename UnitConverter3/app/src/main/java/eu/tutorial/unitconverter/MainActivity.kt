package eu.tutorial.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorial.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    UnitConverterScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Centimeters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    val conversionFactor = remember { mutableStateOf(0.01) }  // Centimeters default
    val oconversionFactor = remember { mutableStateOf(1.00) } // Meters default

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value / oconversionFactor.value * 100.0).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        TopAppBar(
            title = { Text("Unit Converter") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter Value",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                        convertUnits()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Input Dropdown
                    Box {
                        Button(
                            onClick = { iExpanded = true },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = inputUnit)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Input Unit")
                        }
                        DropdownMenu(
                            expanded = iExpanded,
                            onDismissRequest = { iExpanded = false }
                        ) {
                            listOf(
                                "Centimeters" to 0.01,
                                "Meters" to 1.0,
                                "Feets" to 0.3048,
                                "Milimeters" to 0.001
                            ).forEach { (unit, factor) ->
                                DropdownMenuItem(
                                    text = { Text(unit) },
                                    onClick = {
                                        inputUnit = unit
                                        conversionFactor.value = factor
                                        iExpanded = false
                                        convertUnits()
                                    }
                                )
                            }
                        }
                    }

                    // Output Dropdown
                    Box {
                        Button(
                            onClick = { oExpanded = true },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = outputUnit)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Output Unit")
                        }
                        DropdownMenu(
                            expanded = oExpanded,
                            onDismissRequest = { oExpanded = false }
                        ) {
                            listOf(
                                "Centimeters" to 0.01,
                                "Meters" to 1.0,
                                "Feets" to 0.3048,
                                "Milimeters" to 0.001
                            ).forEach { (unit, factor) ->
                                DropdownMenuItem(
                                    text = { Text(unit) },
                                    onClick = {
                                        outputUnit = unit
                                        oconversionFactor.value = factor
                                        oExpanded = false
                                        convertUnits()
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Result:", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = outputValue,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        UnitConverterScreen()
    }
}
