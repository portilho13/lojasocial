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
fun StudentsView() {


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("beneficiarios") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                currentScreen = currentScreen,
                onItemSelected = { id ->
                    currentScreen = id
                    scope.launch { drawerState.close() }
                },
                onLogout = { /* Handle Logout */ }
            )
        }
    ) {
        // Here we render the screen content.
        // We pass the 'onMenuClick' event to the screen so it can open the drawer.
        StudentManagementScreen(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )
    }
}

@Composable
fun StudentManagementScreen(onMenuClick: () -> Unit) {
    // Dados de exemplo baseados na imagem
    val studentsList = listOf(
        Student("Ana Silva", "2021001", "3º Ano"),
        Student("João Santos", "2022045", "2º Ano"),
        Student("Maria Oliveira", "2020123", "Mestrado 1º Ano")
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF8F9FA))) {

        // MODIFIED HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(IPCA_Green_Dark)
                .padding(top = 24.dp, bottom = 24.dp, start = 16.dp, end = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 1. Menu Icon Button (New)
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // 2. Logo (Existing)
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo_ipca),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // 3. Text (Existing)
                Column {
                    Text(
                        text = "Gestão de Estudantes",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp
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
                shape = RoundedCornerShape(12.dp),
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
                            text = "Total de Estudantes Beneficiários",
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

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Adicionar
            Button(
                onClick = { /* Ação de adicionar */ },
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Green_Dark),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Adicionar Estudante", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

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

@Preview(showBackground = true)
@Composable
fun ManagementPreview() {
    StudentsView()
}