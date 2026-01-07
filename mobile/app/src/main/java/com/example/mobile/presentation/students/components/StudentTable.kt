package com.example.mobile.presentation.students.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.presentation.students.Student
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black

@Composable
fun StudentTable(students: List<Student>) {
    Card(
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ), // Cantos arredondados apenas no topo como na imagem
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Cabeçalho da Tabela
            Row(
                modifier = Modifier
                    .background(IPCA_Green_Dark)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Nome",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Nº Estudante",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Ano Curricular",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }

            // Linhas da Tabela
            students.forEachIndexed { index, student ->
                Column {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = student.name,
                            color = Text_Black,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = student.studentNumber,
                            color = Text_Black,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = student.curricularYear,
                            color = Text_Black,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Separador (Divider) exceto após o último item
                    if (index < students.size - 1) {
                        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}