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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.R
import com.example.mobile.presentation.product.ProductEvent
import com.example.mobile.presentation.product.ProductViewModel
import com.example.mobile.presentation.product.components.AddProductDialog
import com.example.mobile.presentation.product.components.ProductItemCard
import com.example.mobile.presentation.product.types.components.AddProductTypeDialog
import com.example.mobile.presentation.product.types.components.ProductTypeCard
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.Text_Black
import com.example.mobile.presentation.ui.theme.Text_Grey


// --- Data Model ---
data class ProductType(
    val id: String,
    val name: String,
    val itemCount: Int // e.g., how many products belong to this type
)

@Composable
fun ProductTypeView(onMenuClick: () -> Unit,viewModel: ProductTypeViewModel = hiltViewModel()) {
    // --- State ---
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }


    // Mostrar erros
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "OK"
            )
            viewModel.onEvent(ProductTypeEvent.ClearError)
        }
    }
    // Mostrar mensagens de sucesso
    LaunchedEffect(state.successMessage) {
        state.successMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK"
            )
            viewModel.onEvent(ProductTypeEvent.ClearSuccess)
        }
    }

    // Dialog State
    var showCreateDialog by remember { mutableStateOf(false) }
    var newTypeName by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onEvent(ProductTypeEvent.ShowAddDialog) },
                containerColor = IPCA_Gold,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Adicionar Categoria") },
                text = { Text("Adicionar Categoria") }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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

            // Products List with Pull to Refresh
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.onEvent(ProductTypeEvent.RefreshProductTypes) },
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    state.isLoading && state.productTypes.isEmpty() -> {
                        // Loading inicial
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = IPCA_Green_Dark,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "A carregar produtos...",
                                    color = Text_Grey,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    else -> {
                        // Lista de produtos
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = state.filteredProductTypes,
                                key = { it.id }
                            ) { productType ->
                                ProductTypeCard(
                                    productType = productType,
//                                    onDeleteClick = {
//                                        //viewModel.onEvent(ProductEvent.DeleteProduct(product.id))
//                                    }
                                )
                            }
                            item { Spacer(modifier = Modifier.height(80.dp)) } // Space for FAB
                        }

                }
            }
        }
    }
    // Add Product Dialog
    if (state.showAddDialog) {
        AddProductTypeDialog(
            isLoading = state.isCreating,
            onDismiss = { viewModel.onEvent(ProductTypeEvent.HideAddDialog) },
            onConfirm = { description->
                viewModel.onEvent(ProductTypeEvent.CreateProductType(description))
            }
        )
    }
//    // 3. Create Dialog
//    if (showCreateDialog) {
//        AlertDialog(
//            onDismissRequest = { showCreateDialog = false },
//            title = {
//                Text(
//                    text = "Novo Tipo de Produto",
//                    color = IPCA_Green_Dark,
//                    fontWeight = FontWeight.Bold
//                )
//            },
//            text = {
//                Column {
//                    Text("Nome da categoria:", fontSize = 14.sp, color = Text_Black)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OutlinedTextField(
//                        value = newTypeName,
//                        onValueChange = { newTypeName = it },
//                        placeholder = { Text("Ex: Mobiliário") },
//                        singleLine = true,
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = IPCA_Green_Dark,
//                            cursorColor = IPCA_Green_Dark
//                        ),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        if (newTypeName.isNotEmpty()) {
//                            // Add logic
//                            productTypes.add(0, ProductType("99", newTypeName, 0))
//                            newTypeName = ""
//                            showCreateDialog = false
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold)
//                ) {
//                    Text("Criar", color = Text_White)
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showCreateDialog = false }) {
//                    Text("Cancelar", color = Color.Gray)
//                }
//            },
//            containerColor = Color.White,
//            shape = RoundedCornerShape(12.dp)
//        )
//    }
}



@Preview
@Composable
fun ProductTypePreview() {
    ProductTypeView(onMenuClick = {})
}