package com.example.mobile.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.R
import com.example.mobile.presentation.Screen
import com.example.mobile.presentation.components.CustomLabel
import com.example.mobile.presentation.components.IPCATextField
import com.example.mobile.presentation.ui.theme.IPCA_Border
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.IPCA_Green_Light
import com.example.mobile.presentation.ui.theme.Text_Grey
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.ipcaInputColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(
//    onNavigateToLogin: () -> Unit,
//    onRegisterSubmission: (String, String, String, String, String) -> Unit
    navController: NavController,
) {
    // Form States
    var name by remember { mutableStateOf("") }
    // Using realistic UI labels mapped to backend values later if needed
    // For this example, we assume the user selects their role.
    val userTypes = listOf("Estudante", "Docente", "Técnico (Admin)")
    var expandedUserType by remember { mutableStateOf(false) }
    var selectedUserType by remember { mutableStateOf("") }

    var contact by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IPCA_Green_Dark)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Make column scrollable to fit smaller screens
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Header Section (Logo & Title)
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                // IMPORTANT: Ensure you imported the SVG as a Vector Asset previously
                painter = painterResource(id = R.drawable.ic_logo_ipca),
                contentDescription = "Logo",
                tint = Text_White,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "Criar Conta",
                color = Text_White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 2. Form Fields

            // Name Input
            IPCATextField(
                label = "Nome Completo",
                value = name,
                onValueChange = { name = it },
                placeholder = "João Silva",
                keyboardType = KeyboardType.Text
            )

            // User Type Dropdown
            CustomLabel(text = "Tipo de Utilizador")
            ExposedDropdownMenuBox(
                expanded = expandedUserType,
                onExpandedChange = { expandedUserType = !expandedUserType },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = selectedUserType.ifEmpty { "Selecione o tipo" },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUserType) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ipcaInputColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedUserType,
                    onDismissRequest = { expandedUserType = false },
                    modifier = Modifier
                        .background(IPCA_Green_Light)
                        .border(1.dp, IPCA_Border, RoundedCornerShape(4.dp))
                ) {
                    userTypes.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = Text_White) },
                            onClick = {
                                selectedUserType = selectionOption
                                expandedUserType = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            colors = MenuDefaults.itemColors(
                                textColor = Text_White,
                                leadingIconColor = Text_White,
                                trailingIconColor = Text_White
                            )
                        )
                    }
                }
            }


            // Contact Input
            IPCATextField(
                label = "Contacto Telefónico",
                value = contact,
                onValueChange = { contact = it },
                placeholder = "912345678",
                keyboardType = KeyboardType.Phone
            )

            // Email Input
            IPCATextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                placeholder = "seu.email@ipca.pt",
                keyboardType = KeyboardType.Email
            )

            // Password Input (Custom due to visibility toggle)
            CustomLabel(text = "Senha")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                placeholder = {
                    Text(
                        "........",
                        color = Text_Grey,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image =
                        if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle Password",
                            tint = Text_Grey
                        )
                    }
                },
                colors = ipcaInputColors(),
                singleLine = true
            )

            // 3. Register Button
            Button(
                onClick = { /*onRegisterSubmission(name, selectedUserType, contact, email, password)*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Registar",
                    color = Text_White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Footer Link back to Login
            val footerText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Text_White)) {
                    append("Já tem uma conta? ")
                }
                withStyle(style = SpanStyle(color = IPCA_Gold, fontWeight = FontWeight.Bold)) {
                    append("Entrar")
                }
            }

            Text(
                text = footerText,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clickable { navController.navigate(Screen.LoginScreen.route) },
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp)) // Extra space for scrolling
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6a")
@Composable
fun RegisterPreview() {
    RegisterView(navController = rememberNavController()/*onNavigateToLogin = {}, onRegisterSubmission = { _,_,_,_,_ -> }*/)
}