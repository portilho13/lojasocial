package com.example.mobile.presentation.students

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.presentation.components.NavigationDrawer
import com.example.mobile.presentation.students.components.StudentTable
import com.example.mobile.presentation.ui.theme.Background_Light
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Black
import kotlinx.coroutines.launch


// --- Modelo de Dados ---
data class Student(
    val name: String,
    val studentNumber: String,
    val curricularYear: String
)

@Composable
fun StudentsView(onMenuClick: () -> Unit, onAddStudent: () -> Unit) {
    // Dados de exemplo baseados na imagem
    val studentsList = listOf(
        Student("Ana Silva", "2021001", "3º Ano"),
        Student("João Santos", "2022045", "2º Ano"),
        Student("Maria Oliveira", "2020123", "Mestrado 1º Ano")
    )
    Scaffold(
        containerColor = Background_Light,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddStudent,
                containerColor = IPCA_Green_Dark,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "Add Student") },
                text = { Text("Adicionar Estudante") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // 1. Header (Standard IPCA Admin Header)
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
                            text = "Gestão de Estudantes",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Visão Geral dos Estudantes",
                            color = Color(0xFFA0C4B5), // Light Green text
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 2. Corpo da Página
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)

            ) {

                // Card de Resumo (Total de Estudantes)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total de Estudantes Beneficiados",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )

                        }
                        Column {
                            Text(
                                text = "${studentsList.size}", // Dinâmico
                                color = IPCA_Green_Dark,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                        }

//                    // Círculo com o número
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .size(50.dp)
//                            .clip(CircleShape)
//                            .background(IPCA_Gold)
//                    ) {
//                        Text(
//                            text = "${studentsList.size}",
//                            color = Color.White,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
                    }
                }


                // Título da Tabela
                Text(
                    text = "Lista de Estudantes",
                    color = IPCA_Green_Dark,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Tabela
                StudentTable(studentsList)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ManagementPreview() {
    StudentsView(onMenuClick = {}, onAddStudent = {} )
}