package com.example.mobile.presentation.stock.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.presentation.stock.StockItem
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black
import com.example.mobile.presentation.ui.theme.Warning_Orange

@Composable
fun StockItemCard(item: StockItem) {
    // Determine color based on urgency
    val expiryColor = when {
        item.expiryDate == null -> IPCA_Green_Dark // No expiry = Safe
        item.daysUntilExpiry <= 3 -> Alert_Red // Critical
        item.daysUntilExpiry <= 14 -> Warning_Orange // Warning
        else -> IPCA_Green_Dark // Safe
    }

    val expiryLabel = when {
        item.expiryDate == null -> "S/ VALIDADE"
        item.daysUntilExpiry < 0 -> "EXPIRADO"
        item.daysUntilExpiry == 0 -> "HOJE"
        else -> "${item.daysUntilExpiry} dias"
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row 1: Name and Quantity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.productName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Text_Black
                    )
                    Text(
                        text = "Lote #${item.id}",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Quantity Badge
                Surface(
                    color = Background_Light,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "${item.quantity} ${item.unit}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = IPCA_Gold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.5f))

            // Row 2: Location and Expiry
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Location Info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.location,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Expiry Info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, tint = expiryColor, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    // Parse and Format Display Date if needed, or just show raw string
                    // Ideally we should format "2026-12-31" to "31/12/2026", but for now showing the string from backend
                    val displayDate = if (item.expiryDate != null) {
                         try {
                              // Simple heuristic to show short date if it's ISO
                              item.expiryDate.take(10) 
                         } catch (e:Exception) { item.expiryDate }
                    } else "N/A"

                    Text(
                        text = displayDate,
                        fontSize = 13.sp,
                        color = Text_Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Days left Badge
                    Surface(
                        color = expiryColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = expiryLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = expiryColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Row 3: Action Buttons (Edit/Delete)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { /* Open Edit Dialog/Screen */ },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar", fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(
                    onClick = { /* Handle Delete */ },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Icon(Icons.Default.Delete, null, modifier = Modifier.size(16.dp), tint = Alert_Red)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Eliminar", fontSize = 12.sp, color = Alert_Red)
                }
            }
        }
    }
}