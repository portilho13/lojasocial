package com.example.mobile.presentation.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R // REPLACE with your package
import com.example.mobile.presentation.components.CustomLabel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Border
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.IPCA_Green_Light
import com.example.mobile.presentation.ui.theme.Text_Grey
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.ipcaInputColors
import kotlin.text.ifEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockManagementScreen(
    onNavigateBack: () -> Unit,
    onSaveStock: (String, String, String, String, Long) -> Unit // ProductId, Qtd, Unit, Location, Date
) {
    // --- Mock Data ---
    val productList =
        listOf("Leite Meio Gordo", "Arroz Agulha", "Detergente", "Pão de Forma", "Iogurte Natural")
    val locationList =
        listOf("Armazém A", "Armazém B", "Frigorífico 1", "Frigorífico 2", "Despensa Seca")
    val unitList = listOf("Un", "Kg", "L", "Cx")

    // --- State ---
    var selectedProduct by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Un") }
    var selectedLocation by remember { mutableStateOf("") }

    // Date Picker State
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Dropdown UI States
    var productExpanded by remember { mutableStateOf(false) }
    var locationExpanded by remember { mutableStateOf(false) }
    var unitExpanded by remember { mutableStateOf(false) }

    // Helper to format date
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateString = selectedDateMillis?.let { dateFormatter.format(Date(it)) } ?: ""

    Scaffold(
        containerColor = IPCA_Green_Dark,
        topBar = {
            // Simple Top Bar for sub-pages
            TopAppBar(
                title = {
                    Text(
                        "Entrada de Stock",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ChevronLeft, "Back", tint = Text_White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IPCA_Green_Dark,
                    titleContentColor = Text_White
                )
            )
        },
        bottomBar = {
            // Bottom Action Button
            Surface(
                color = IPCA_Gold,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {

            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            CustomLabel(text = "Detalhes do Produto")
            ExposedDropdownMenuBox(
                expanded = productExpanded,
                onExpandedChange = { productExpanded = !productExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = selectedProduct.ifEmpty { "Selecione o Produto" },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = productExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ipcaInputColors()
                )
                ExposedDropdownMenu(
                    expanded = productExpanded,
                    onDismissRequest = { productExpanded = false },
                    modifier = Modifier
                        .background(IPCA_Green_Light)
                        .border(1.dp, IPCA_Border, RoundedCornerShape(4.dp))
                ) {
                    productList.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = Text_White) },
                            onClick = {
                                selectedProduct = selectionOption
                                productExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            colors = MenuDefaults.itemColors(
                                textColor = Text_White,
                                leadingIconColor = Text_White,
                                trailingIconColor = Text_White
                            )
                        )
                    }
                }
            }

            // 2. Quantity and Unit Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Quantity
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { if (it.all { char -> char.isDigit() }) quantity = it },
                    placeholder = {
                        Text(
                            "Quantidade",
                            color = Text_White,
                            fontSize = 14.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = ipcaInputColors(),
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(8.dp)
                )

                // Unit
                ExposedDropdownMenuBox(
                    expanded = unitExpanded,
                    onExpandedChange = { unitExpanded = !unitExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedUnit,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ipcaInputColors()
                    )
                    ExposedDropdownMenu(
                        expanded = unitExpanded,
                        onDismissRequest = { unitExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        unitList.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit) },
                                onClick = {
                                    selectedUnit = unit
                                    unitExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            // 3. Storage Details
            Text("Armazenamento e Validade", color = Text_White, fontWeight = FontWeight.Bold)

            // Location Dropdown
            ExposedDropdownMenuBox(
                expanded = locationExpanded,
                onExpandedChange = { locationExpanded = !locationExpanded }
            ) {
                OutlinedTextField(
                    value = selectedLocation,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            "Local de Armazenamento",
                            color = Text_White,
                            fontSize = 14.sp
                        )
                    },
                    placeholder = { Text("Ex: Armazém A") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = locationExpanded) },
                    colors = ipcaInputColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                ExposedDropdownMenu(
                    expanded = locationExpanded,
                    onDismissRequest = { locationExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    locationList.forEach { loc ->
                        DropdownMenuItem(
                            text = { Text(loc) },
                            onClick = {
                                selectedLocation = loc
                                locationExpanded = false
                            }
                        )
                    }
                }
            }

            // Expiry Date Picker Field
            Box {
                OutlinedTextField(
                    value = dateString,
                    onValueChange = {},
                    label = { Text("Data de Validade", color = Text_White, fontSize = 14.sp) },
                    placeholder = { Text("DD/MM/AAAA") },
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Default.CalendarToday, null, tint = Text_Grey) },
                    colors = ipcaInputColors(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    // Handle Click to open dialog
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        showDatePicker = true
                                    }
                                }
                            }
                        }
                )
            }
            Button(
                onClick = {
                    if (selectedProduct.isNotEmpty() && quantity.isNotEmpty() && selectedDateMillis != null) {
                        onSaveStock(
                            selectedProduct,
                            quantity,
                            selectedUnit,
                            selectedLocation,
                            selectedDateMillis!!
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                shape = RoundedCornerShape(8.dp),
                // Disable if critical fields are empty
                //enabled = selectedProduct.isNotEmpty() && quantity.isNotEmpty() && selectedLocation.isNotEmpty()
            ) {
                Text(
                    text = "Confirmar Entrada",
                    color = Text_White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // 4. Material 3 Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDateMillis = datePickerState.selectedDateMillis
                        showDatePicker = false
                    }
                ) { Text("OK", color = IPCA_Green_Dark) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(
                        "Cancelar",
                        color = Color.Gray
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = IPCA_Green_Dark,
                    todayDateBorderColor = IPCA_Green_Dark,
                    todayContentColor = IPCA_Green_Dark
                )
            )
        }
    }
}

@Preview
@Composable
fun StockManagementPreview() {
    StockManagementScreen(onNavigateBack = {}, onSaveStock = { _, _, _, _, _ -> })
}