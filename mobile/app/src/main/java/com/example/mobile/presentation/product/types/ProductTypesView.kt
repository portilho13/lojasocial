package com.example.mobile.presentation.product.types

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile.R
import com.example.mobile.presentation.product.types.components.AddProductTypeDialog
import com.example.mobile.presentation.product.types.components.ProductTypeCard
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Grey

@Composable
fun ProductTypeView(
    onMenuClick: () -> Unit,
    viewModel: ProductTypeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handlers for side effects
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it, actionLabel = "OK")
            viewModel.onEvent(ProductTypeEvent.ClearError)
        }
    }

    LaunchedEffect(state.successMessage) {
        state.successMessage?.let {
            snackbarHostState.showSnackbar(it, actionLabel = "OK")
            viewModel.onEvent(ProductTypeEvent.ClearSuccess)
        }
    }

    Scaffold(
        containerColor = Background_Light,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onEvent(ProductTypeEvent.ShowAddDialog) },
                containerColor = IPCA_Gold,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Adicionar Categoria") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // Header
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
                            color = Color(0xFFA0C4B5),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Main Content
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.onEvent(ProductTypeEvent.RefreshProductTypes) },
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.isLoading && state.productTypes.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = IPCA_Green_Dark)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.filteredProductTypes,
                            key = { it.id }
                        ) { typeItem ->
                            // Correct usage of the card component
                            ProductTypeCard(productType = typeItem)
                        }
                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }
        }
    }

    // Dialog logic
    if (state.showAddDialog) {
        AddProductTypeDialog(
            isLoading = state.isCreating,
            onDismiss = { viewModel.onEvent(ProductTypeEvent.HideAddDialog) },
            onConfirm = { description ->
                viewModel.onEvent(ProductTypeEvent.CreateProductType(description))
            }
        )
    }
}