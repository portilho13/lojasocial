package com.example.mobile.presentation.product.types.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.domain.models.ProductType
import com.example.mobile.presentation.ui.theme.IPCA_Border
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.IPCA_Green_Light
import com.example.mobile.presentation.ui.theme.Text_Grey
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.ipcaInputColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    productTypes: List<ProductType>,
    isLoading: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: (name: String, description: String, typeId: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<ProductType?>(null) }
    var expandedTypeDropdown by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = {
            Text(
                text = "Adicionar Produto",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = IPCA_Green_Dark
            )
        },
        text = {
            Column {
                // Nome
                Text(
                    text = "Nome do Produto",
                    color = Text_Grey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    placeholder = { Text("Ex: Leite Meio Gordo", color = Text_Grey) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ipcaInputColors(),
                    singleLine = true,
                    enabled = !isLoading
                )

                // Tipo de Produto
                Text(
                    text = "Tipo de Produto",
                    color = Text_Grey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expandedTypeDropdown,
                    onExpandedChange = {
                        if (!isLoading) expandedTypeDropdown = !expandedTypeDropdown
                    }
                ) {
                    OutlinedTextField(
                        value = selectedType?.description ?: "Selecione o tipo",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTypeDropdown)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ipcaInputColors(),
                        enabled = !isLoading
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTypeDropdown,
                        onDismissRequest = { expandedTypeDropdown = false },
                        modifier = Modifier
                            .background(IPCA_Green_Light)
                            .border(1.dp, IPCA_Border, RoundedCornerShape(4.dp))
                    ) {
                        productTypes.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(type.description, color = Text_White, fontWeight = FontWeight.Bold)
                                        type.description?.let { desc ->
                                            Text(desc, color = Text_Grey, fontSize = 12.sp)
                                        }
                                    }
                                },
                                onClick = {
                                    selectedType = type
                                    expandedTypeDropdown = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                colors = MenuDefaults.itemColors(
                                    textColor = Text_White
                                )
                            )
                        }
                    }
                }

                if (isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        color = IPCA_Green_Dark,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedType?.let { type ->
                        onConfirm(name, description, type.id)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading && name.isNotBlank() && selectedType != null
            ) {
                Text("Adicionar", color = Text_White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text("Cancelar", color = IPCA_Green_Dark, fontWeight = FontWeight.Bold)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}