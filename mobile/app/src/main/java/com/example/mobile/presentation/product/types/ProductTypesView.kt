package com.example.mobile.presentation.product.types

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.presentation.product.types.components.ProductTypeCard
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.Text_Black


// --- Data Model ---
data class ProductType(
    val id: String,
    val name: String,
    val itemCount: Int // e.g., how many products belong to this type
)

@Composable
fun ProductTypeView(onMenuClick: () -> Unit) {
    // --- State ---
    // Mock Data
    val initialTypes = listOf(
        ProductType("1", "Alimentar (Secos)", 120),
        ProductType("2", "Alimentar (Frescos)", 45),
        ProductType("3", "Higiene e Limpeza", 30),
        ProductType("4", "Material Escolar", 85),
        ProductType("5", "Utensílios de Cozinha", 12)
    )
    val productTypes = remember { mutableStateListOf(*initialTypes.toTypedArray()) }

    // Dialog State
    var showCreateDialog by remember { mutableStateOf(false) }
    var newTypeName by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = IPCA_Gold,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Add Type") },
                text = { Text("Adicionar Categoria") }
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
                            text = "Categorias de Produtos",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Definição de Categorias",
                            color = Color(0xFFA0C4B5), // Light Green text
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Content List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Categorias Ativas",
                        color = IPCA_Green_Dark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(productTypes) { type ->
                    ProductTypeCard(type)
                }

                // Extra spacer for FAB
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    // 3. Create Dialog
    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = {
                Text(
                    text = "Novo Tipo de Produto",
                    color = IPCA_Green_Dark,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text("Nome da categoria:", fontSize = 14.sp, color = Text_Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newTypeName,
                        onValueChange = { newTypeName = it },
                        placeholder = { Text("Ex: Mobiliário") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IPCA_Green_Dark,
                            cursorColor = IPCA_Green_Dark
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTypeName.isNotEmpty()) {
                            // Add logic
                            productTypes.add(0, ProductType("99", newTypeName, 0))
                            newTypeName = ""
                            showCreateDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold)
                ) {
                    Text("Criar", color = Text_White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
    }
}



@Preview
@Composable
fun ProductTypePreview() {
    ProductTypeView(onMenuClick = {})
}