package com.example.mobile.presentation.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.presentation.product.Product
import com.example.mobile.presentation.product.ProductView
import com.example.mobile.presentation.product.types.ProductType
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black

val product = Product("1", "Leite Meio Gordo", "Pacote 1L", "Alimentar (Frescos)")

@Composable
fun ProductItemCard(product: Product) {

    var showDialog by remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(IPCA_Green_Dark.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = IPCA_Green_Dark,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Text_Black
                )
                Text(
                    text = product.typeName,
                    fontSize = 12.sp,
                    color = IPCA_Gold,
                    fontWeight = FontWeight.Medium
                )
                if (product.description.isNotEmpty()) {
                    Text(
                        text = product.description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Options Icon
            IconButton(onClick = { showDialog = true}) {
                Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
            }
        }
    }

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
@Preview
@Composable
fun ProductPreview() {
    ProductItemCard(product)
}