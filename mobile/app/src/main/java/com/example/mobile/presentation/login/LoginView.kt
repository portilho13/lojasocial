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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    // Efeito para navegar após login bem-sucedido
    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
            viewModel.resetLoginSuccess()
        }
    }

    LaunchedEffect(state.error, state.isLoginSuccessful) {
        if (state.error != null && !state.isLoginSuccessful) {
            snackbarHostState.showSnackbar(
                message = state.error!!,
                actionLabel = "OK"
            )
            viewModel.onEvent(LoginEvent.ClearError)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IPCA_Green_Dark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Icon(
                painter = painterResource(id = R.drawable.ic_logo_ipca),
                contentDescription = "Logo IPCA",
                tint = Text_White,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Header
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
            CustomLabel(text = "Email")
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("seu.email@ipca.pt", color = Text_Grey)
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                colors = ipcaInputColors(),
                singleLine = true,
                isError = state.emailError != null,
                supportingText = state.emailError?.let { error ->
                    { Text(text = error, color = IPCA_Gold) }
                },
                enabled = !state.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            CustomLabel(text = "Senha")
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
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
                visualTransformation = if (state.isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.onEvent(LoginEvent.Login)
                    }
                ),
                trailingIcon = {
                    val image = if (state.isPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(LoginEvent.TogglePasswordVisibility) }
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle Password",
                            tint = Text_Grey
                        )
                    }
                },
                colors = ipcaInputColors(),
                singleLine = true,
                isError = state.passwordError != null,
                supportingText = state.passwordError?.let { error ->
                    { Text(text = error, color = IPCA_Gold) }
                },
                enabled = !state.isLoading
            )

            // Remember Me & Forgot Password
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.rememberMe,
                        onCheckedChange = {
                            viewModel.onEvent(LoginEvent.RememberMeChanged(it))
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = IPCA_Gold,
                            uncheckedColor = Text_Grey,
                            checkmarkColor = Text_White
                        ),
                        modifier = Modifier.size(20.dp),
                        enabled = !state.isLoading
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
                    modifier = Modifier.clickable(enabled = !state.isLoading) {
                        // TODO: Navegar para recuperação de senha
                    }
                )
            }

            // Login Button
            Button(
                onClick = { viewModel.onEvent(LoginEvent.Login) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IPCA_Gold),
                shape = RoundedCornerShape(8.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Text_White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Entrar",
                        color = Text_White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
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
                    .clickable(enabled = !state.isLoading) {
                        navController.navigate(Screen.RegisterScreen.route)
                    },
                fontSize = 14.sp
            )

            Text(
                text = "© 2025 Serviços de Ação Social IPCA",
                color = Text_Grey,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Snackbar para mostrar erros
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginView(navController = rememberNavController())
}