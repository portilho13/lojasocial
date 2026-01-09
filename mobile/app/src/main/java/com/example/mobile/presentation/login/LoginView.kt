package com.example.mobile.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.mobile.presentation.ui.theme.IPCA_Gold
import com.example.mobile.presentation.ui.theme.IPCA_Green_Dark
import com.example.mobile.presentation.ui.theme.Text_Grey
import com.example.mobile.presentation.ui.theme.Text_White
import com.example.mobile.presentation.ui.theme.ipcaInputColors

// --- Color Definitions based on the Image ---


@Composable
fun LoginView(
    navController: NavController,
) {
    // State management
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Main Container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IPCA_Green_Dark)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_logo_ipca), // Placeholder
                contentDescription = "Logo",
                tint = Text_White,
                modifier = Modifier.size(80.dp)
            )

            // 2. Header Text
            Text(
                text = "Bem-vindo",
                color = Text_White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Entre para aceder à sua conta",
                color = Text_Grey,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            // Email Input
            IPCATextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                placeholder = "seu.email@ipca.pt",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Password Input
            CustomLabel(text = "Senha")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
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

            // 5. Checkbox and Forgot Password
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = IPCA_Gold,
                            uncheckedColor = Text_Grey,
                            checkmarkColor = Text_White
                        ),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Lembrar-me",
                        color = Text_White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Esqueceu-se da senha?",
                    color = IPCA_Gold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* Handle click */ }
                )
            }

            // 6. Login Button
            Button(
                onClick = { navController.navigate(Screen.HomeScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Entrar",
                    color = Text_White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 7. Footer
            val footerText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Text_White)) {
                    append("Não tem uma conta? ")
                }
                withStyle(style = SpanStyle(color = IPCA_Gold, fontWeight = FontWeight.Bold)) {
                    append("Registar")
                }
            }

            Text(
                text = footerText,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clickable { navController.navigate(Screen.RegisterScreen.route) },
                fontSize = 14.sp
            )

            Text(
                text = "© 2025 Serviços de Ação Social IPCA",
                color = Text_Grey,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

// Helper composable for labels above text fields


@Preview(showBackground = true)
@Composable
fun LoginPreview(

) {
    LoginView(navController = rememberNavController())
}