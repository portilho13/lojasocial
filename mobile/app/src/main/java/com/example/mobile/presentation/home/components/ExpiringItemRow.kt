package com.example.mobile.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.presentation.home.ExpiringItem
import com.example.mobile.presentation.ui.theme.Alert_Red
import com.example.mobile.presentation.ui.theme.Text_Black
import com.example.mobile.presentation.ui.theme.Warning_Orange

@Composable
fun ExpiringItemRow(item: ExpiringItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = item.productName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Text_Black
            )
            Text(text = item.location, fontSize = 11.sp, color = Color.Gray)
        }

        Text(
            text = "${item.quantity} un",
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = Text_Black
        )

        // Highlight urgencies in Red
        val daysColor = if (item.daysLeft <= 2) Alert_Red else Warning_Orange
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.WarningAmber,
                null,
                tint = daysColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${item.daysLeft} dias",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = daysColor
            )
        }
    }
}