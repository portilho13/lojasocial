package com.example.mobile.presentation.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.domain.models.Product
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Grey


@Composable
fun ProductItemCard(product: Product, onDeleteClick: () -> Unit) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = IPCA_Green_Dark
                )

                if (!product.description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = Text_Grey
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                product.typeDescription?.let { typeName ->
                    Row(
                        modifier = Modifier
                            .background(
                                color = IPCA_Gold.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = typeName,
                            fontSize = 12.sp,
                            color = IPCA_Gold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            IconButton(
                onClick = { showDeleteConfirmation = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Alert_Red
                )
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmation) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Eliminar Produto") },
            text = { Text("Tem a certeza que deseja eliminar '${product.name}'?") },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        //onDeleteClick()
                        showDeleteConfirmation = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Alert_Red)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(
                    onClick = { showDeleteConfirmation = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
//    var showDialog by remember { mutableStateOf(false) }
//    Card(
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        shape = RoundedCornerShape(12.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Product Icon
//            Box(
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape)
//                    .background(IPCA_Green_Dark.copy(alpha = 0.05f)),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ShoppingBag,
//                    contentDescription = null,
//                    tint = IPCA_Green_Dark,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            // Details
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = product.name,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp,
//                    color = Text_Black
//                )
//                Text(
//                    text = product.typeName,
//                    fontSize = 12.sp,
//                    color = IPCA_Gold,
//                    fontWeight = FontWeight.Medium
//                )
//                if (product.description.isNotEmpty()) {
//                    Text(
//                        text = product.description,
//                        fontSize = 12.sp,
//                        color = Color.Gray,
//                        maxLines = 1,
//                        modifier = Modifier.padding(top = 2.dp)
//                    )
//                }
//            }
//
//            // Options Icon
//            IconButton(onClick = { showDialog = true}) {
//                Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
//            }
//        }
//    }

//    // 3. Create Dialog
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
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
//                    colors = ButtonDefaults.buttonColors(containerColor = IPCA_Green_Dark)
//                ) {
//                    Text("Criar")
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
