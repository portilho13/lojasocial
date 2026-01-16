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
import com.example.mobile.presentation.students.Student
import com.example.mobile.presentation.ui.theme.Text_Black


val student = Student("Ana Silva", "2021001", "3º Ano")

@Composable
fun StudentItemRow(student: Student) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = student.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Text_Black
            )
            Text(text = student.curricularYear, fontSize = 11.sp, color = Color.Gray)
        }

        Text(
            text = "${student.studentNumber}",
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = Text_Black
        )

    }
}

@Preview(showBackground = true)
@Composable
fun StudentRowPreview() {
    StudentItemRow(student)
}