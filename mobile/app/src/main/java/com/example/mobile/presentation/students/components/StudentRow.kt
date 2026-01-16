package com.example.mobile.presentation.students.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.data.remote.dto.Student
import com.example.mobile.presentation.ui.theme.Text_Black

@Composable
fun StudentItemRow(student: Student) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1.5f)) {
            Text(
                text = student.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Text_Black
            )
            Text(
                text = "${student.academicYear}º Ano",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }

        Text(
            text = student.studentNumber,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = Text_Black
        )

        Text(
            text = student.course,
            modifier = Modifier.weight(1.5f),
            fontSize = 14.sp,
            color = Text_Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentRowPreview() {
    StudentItemRow(
        student = Student(
            id = "1",
            name = "Ana Silva",
            studentNumber = "2021001",
            course = "Engenharia Informática",
            academicYear = 3,
            socialSecurityNumber = "123456789",
            contact = "912345678",
            email = "ana.silva@ipca.pt"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun StudentRowPreview2() {
    StudentItemRow(
        student = Student(
            id = "2",
            name = "João Santos",
            studentNumber = "2022045",
            course = "Gestão de Empresas",
            academicYear = 2,
            socialSecurityNumber = "987654321",
            contact = "913456789",
            email = "joao.santos@ipca.pt"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun StudentRowPreview3() {
    StudentItemRow(
        student = Student(
            id = "3",
            name = "Maria Oliveira com Nome Muito Grande",
            studentNumber = "2020123",
            course = "Design Gráfico e Multimédia",
            academicYear = 1,
            socialSecurityNumber = "456789123",
            contact = "914567890",
            email = "maria.oliveira@ipca.pt"
        )
    )
}