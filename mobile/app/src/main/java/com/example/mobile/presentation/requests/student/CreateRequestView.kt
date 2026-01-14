package com.example.mobile.presentation.requests.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShoppingBag
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
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black

// --- Temporary Model for Creation ---
data class NewRequestItem(
    val productId: String,
    val productName: String,
    val quantity: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRequestView(
    onNavigateBack: () -> Unit,
    onSubmitRequest: (String, List<NewRequestItem>) -> Unit // Observation, List of Items
) {
    // --- Mock Data (Available Products to Request) ---
    val availableProducts = listOf(
        Pair("p1", "Arroz Agulha 1kg"),
        Pair("p2", "Massa Esparguete 500g"),
        Pair("p3", "Leite Meio Gordo 1L"),
        Pair("p4", "Atum em Lata"),
        Pair("p5", "Feijão Preto Lata"),
        Pair("p6", "Óleo Alimentar 1L")
    )

    // --- State ---
    var observation by remember { mutableStateOf("") }
    val addedItems = remember { mutableStateListOf<NewRequestItem>() }

    // Dialog State
    var showAddItemDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Background_Light,
        topBar = {
            // Standard Simple TopBar
            TopAppBar(
                title = { Text("Novo Pedido de Apoio", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = IPCA_Green_Dark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = IPCA_Green_Dark
                )
            )
        },
        bottomBar = {
            // Submit Button Footer
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onSubmitRequest(observation, addedItems) },
                    modifier = Modifier
                        .padding(20.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                    shape = RoundedCornerShape(8.dp),
                    enabled = addedItems.isNotEmpty() && observation.isNotEmpty()
                ) {
                    Icon(Icons.Default.Save, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submeter Pedido", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            // 1. Observation / Reason Input
            Text(
                text = "Motivo do Pedido",
                color = IPCA_Green_Dark,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = observation,
                onValueChange = { observation = it },
                placeholder = { Text("Ex: Preciso de apoio alimentar para esta semana pois...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IPCA_Green_Dark,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Items Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Itens Necessários",
                    color = IPCA_Green_Dark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                TextButton(onClick = { showAddItemDialog = true }) {
                    Icon(Icons.Default.Add, null, tint = IPCA_Gold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Adicionar Item", color = IPCA_Gold, fontWeight = FontWeight.Bold)
                }
            }

            // 3. Items List
            if (addedItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ShoppingBag, null, tint = Color.LightGray, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Ainda não adicionou itens.", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(addedItems) { item ->
                        RequestItemRow(
                            item = item,
                            onRemove = { addedItems.remove(item) }
                        )
                    }
                }
            }
        }
    }

    // 4. Dialog to Add Item
    if (showAddItemDialog) {
        AddItemDialog(
            products = availableProducts,
            onDismiss = { showAddItemDialog = false },
            onConfirm = { product, qty ->
                addedItems.add(NewRequestItem(product.first, product.second, qty))
                showAddItemDialog = false
            }
        )
    }
}

@Composable
fun RequestItemRow(item: NewRequestItem, onRemove: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = item.productName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Text_Black)
                Text(text = "Quantidade: ${item.quantity}", fontSize = 12.sp, color = Color.Gray)
            }

            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Alert_Red.copy(alpha = 0.7f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
    products: List<Pair<String, String>>, // ID, Name
    onDismiss: () -> Unit,
    onConfirm: (Pair<String, String>, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Pair<String, String>?>(null) }
    var quantity by remember { mutableStateOf("1") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Produto", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // Product Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedProduct?.second ?: "Selecione...",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Produto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IPCA_Green_Dark,
                            focusedLabelColor = IPCA_Green_Dark
                        ),
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        products.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.second) },
                                onClick = {
                                    selectedProduct = product
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Quantity Input
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { if (it.all { char -> char.isDigit() }) quantity = it },
                    label = { Text("Quantidade") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IPCA_Green_Dark,
                        focusedLabelColor = IPCA_Green_Dark
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedProduct != null && quantity.isNotEmpty()) {
                        onConfirm(selectedProduct!!, quantity.toIntOrNull() ?: 1)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Green_Dark),
                enabled = selectedProduct != null && quantity.isNotEmpty()
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color.Gray)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview
@Composable
fun CreateRequestPreview() {
    CreateRequestView(onNavigateBack = {}, onSubmitRequest = { _, _ -> })
}