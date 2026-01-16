package com.example.mobile.presentation.students.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.presentation.students.StudentTableItem
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark

@Composable
fun StudentTable(students: List<StudentTableItem>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IPCA_Green_Dark)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Nome",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1.5f)
                )
                Text(
                    text = "Número",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Curso",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1.5f)
                )
                Text(
                    text = "Ano",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(0.8f)
                )
            }

            // Body
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                items(students) { student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = student.name,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = student.studentNumber,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = student.course,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = student.academicYear,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(0.8f)
                        )
                    }
                }
            }
        }
    }
}