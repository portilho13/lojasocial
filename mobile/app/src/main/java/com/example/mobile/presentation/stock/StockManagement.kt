package com.example.mobile.presentation.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.domain.models.CreateStockRequest
import com.example.mobile.presentation.components.CustomLabel
import com.example.mobile.presentation.product.ProductViewModel
import com.example.mobile.presentation.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockManagementScreen(
    onNavigateBack: () -> Unit,
    stockViewModel: StockViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val stockUiState by stockViewModel.uiState.collectAsState()
    val productState by productViewModel.state.collectAsState()

    // Load products on first composition
    LaunchedEffect(Unit) {
        productViewModel.onEvent(com.example.mobile.presentation.product.ProductEvent.LoadProducts)
    }

    // --- State ---
    var selectedProductId by remember { mutableStateOf("") }
    var selectedProductName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var productExpanded by remember { mutableStateOf(false) }
    var locationExpanded by remember { mutableStateOf(false) }

    // Mock locations
    val locationList = listOf(
        "Armazém A", "Armazém B", "Frigorífico 1",
        "Frigorífico 2", "Despensa Seca"
    )

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateString = selectedDateMillis?.let { dateFormatter.format(Date(it)) } ?: ""

    Scaffold(
        containerColor = IPCA_Green_Dark,
        topBar = {
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

            // Product Dropdown
            ExposedDropdownMenuBox(
                expanded = productExpanded,
                onExpandedChange = { productExpanded = !productExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = selectedProductName.ifEmpty { "Selecione o Produto" },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = productExpanded)
                    },
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
                    if (productState.isLoading) {
                        DropdownMenuItem(
                            text = { Text("Carregando...", color = Text_White) },
                            onClick = {},
                            enabled = false
                        )
                    } else if (productState.products.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Nenhum produto disponível", color = Text_White) },
                            onClick = {},
                            enabled = false
                        )
                    } else {
                        productState.products.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.name, color = Text_White) },
                                onClick = {
                                    selectedProductId = product.id
                                    selectedProductName = product.name
                                    productExpanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                colors = MenuDefaults.itemColors(
                                    textColor = Text_White
                                )
                            )
                        }
                    }
                }
            }

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
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

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
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = locationExpanded)
                    },
                    colors = ipcaInputColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                ExposedDropdownMenu(
                    expanded = locationExpanded,
                    onDismissRequest = { locationExpanded = false },
                    modifier = Modifier
                        .background(IPCA_Green_Light)
                        .border(1.dp, IPCA_Border, RoundedCornerShape(4.dp))
                ) {
                    locationList.forEach { loc ->
                        DropdownMenuItem(
                            text = { Text(loc, color = Text_White) },
                            onClick = {
                                selectedLocation = loc
                                locationExpanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = Text_White
                            )
                        )
                    }
                }
            }

            // Expiry Date Picker
            Box {
                OutlinedTextField(
                    value = dateString,
                    onValueChange = {},
                    label = { Text("Data de Validade", color = Text_White, fontSize = 14.sp) },
                    placeholder = { Text("DD/MM/AAAA") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.CalendarToday, null, tint = Text_Grey)
                    },
                    colors = ipcaInputColors(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
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

            // Submit Button
            Button(
                onClick = {
                    if (selectedProductId.isNotEmpty() &&
                        quantity.isNotEmpty() &&
                        selectedLocation.isNotEmpty() &&
                        selectedDateMillis != null) {

                        // Convert millis to ISO 8601 format using SimpleDateFormat
                        val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                        isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
                        val expiryDate = isoFormatter.format(Date(selectedDateMillis!!))

                        val request = CreateStockRequest(
                            id = selectedProductId,
                            quantity = quantity.toInt(),
                            location = selectedLocation,
                            expiryDate = expiryDate
                        )

                        stockViewModel.createStock(request)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                shape = RoundedCornerShape(8.dp),
                enabled = !stockUiState.isLoading &&
                        selectedProductId.isNotEmpty() &&
                        quantity.isNotEmpty() &&
                        selectedLocation.isNotEmpty() &&
                        selectedDateMillis != null
            ) {
                if (stockUiState.isLoading) {
                    CircularProgressIndicator(
                        color = Text_White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Confirmar Entrada",
                        color = Text_White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Error message
            stockUiState.error?.let { error ->
                Text(
                    text = error,
                    color = Alert_Red,
                    fontSize = 14.sp
                )
            }
        }
    }

    // Date Picker Dialog
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
                    Text("Cancelar", color = Color.Gray)
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

    // Handle success
    LaunchedEffect(stockUiState.successMessage) {
        stockUiState.successMessage?.let {
            // Stock created successfully, navigate back
            stockViewModel.clearMessages()
            onNavigateBack()
        }
    }
}