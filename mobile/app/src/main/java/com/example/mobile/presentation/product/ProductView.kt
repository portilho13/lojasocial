package com.example.mobile.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.presentation.product.components.AddProductDialog
import com.example.mobile.presentation.product.components.ProductItemCard
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark

// --- Models ---
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val typeName: String // In a real app, you might store typeId
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductView(onMenuClick: () -> Unit) {
    // --- Mock Data ---
    // In a real app, fetch these from 'list products' API
    val initialProducts = listOf(
        Product("1", "Leite Meio Gordo", "Pacote 1L", "Alimentar (Frescos)"),
        Product("2", "Arroz Agulha", "Pacote 1kg", "Alimentar (Secos)"),
        Product("3", "Detergente Loiça", "Limão 500ml", "Higiene"),
        Product("4", "Caneta Azul", "Bic Cristal", "Material Escolar"),
        Product("5", "Caderno A4", "Pautado 80fls", "Material Escolar")
    )

    // In a real app, fetch these from 'list producttypes' API
    val availableTypes = listOf("Alimentar (Secos)", "Alimentar (Frescos)", "Higiene", "Material Escolar")

    // --- State ---
    var products by remember { mutableStateOf(initialProducts) }
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    // Filter logic
    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.typeName.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = IPCA_Gold,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Add Product") },
                text = { Text("Adicionar Produto") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // 1. Header (Standard IPCA Admin Header)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    .padding(top = 24.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo_ipca),
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Catálogo de Produtos",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Definição de Artigos",
                            color = Color(0xFFA0C4B5), // Light Green text
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Search Bar
            PaddingValues(20.dp).let {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    placeholder = { Text("Pesquisar produto ou tipo...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = IPCA_Green_Dark) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = IPCA_Green_Dark,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true
                )
            }

            // 3. Products List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductItemCard(product)
                }
                item { Spacer(modifier = Modifier.height(80.dp)) } // Space for FAB
            }
        }
    }

    // 4. Add Product Dialog
    if (showAddDialog) {
        AddProductDialog(
            availableTypes = availableTypes,
            onDismiss = { showAddDialog = false },
            onConfirm = { name, desc, type ->
                // Add new product to list (In real app, call API here)
                val newId = (products.size + 1).toString()
                products = products + Product(newId, name, desc, type)
                showAddDialog = false
            }
        )
    }
}




@Preview
@Composable
fun ProductScreenPreview() {
    ProductView(onMenuClick = {})
}