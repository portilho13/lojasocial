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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var expandedUserType by remember { mutableStateOf(false) }
    val userTypes = listOf("Estudante", "Docente", "Técnico (Admin)")

    // Navegação após registo bem-sucedido
    LaunchedEffect(state.isRegisterSuccessful) {
        if (state.isRegisterSuccessful) {
            snackbarHostState.showSnackbar(
                message = "Conta criada com sucesso! Faça login para continuar.",
                actionLabel = "OK"
            )
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.RegisterScreen.route) { inclusive = true }
            }
            viewModel.resetRegisterSuccess()
        }
    }

    // Mostrar erros
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "OK"
            )
            viewModel.onEvent(RegisterEvent.ClearError)
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
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Logo
            Icon(
                painter = painterResource(id = R.drawable.ic_logo_ipca),
                contentDescription = "Logo IPCA",
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

            // Nome
            CustomLabel(text = "Nome Completo")
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(RegisterEvent.NameChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("João Silva", color = Text_Grey) },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                colors = ipcaInputColors(),
                singleLine = true,
                isError = state.nameError != null,
                supportingText = state.nameError?.let { error ->
                    { Text(text = error, color = IPCA_Gold) }
                },
                enabled = !state.isLoading
            )

            // Tipo de Utilizador
            CustomLabel(text = "Tipo de Utilizador")
            ExposedDropdownMenuBox(
                expanded = expandedUserType,
                onExpandedChange = {
                    if (!state.isLoading) expandedUserType = !expandedUserType
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = state.userType.ifEmpty { "Selecione o tipo" },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUserType)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ipcaInputColors(),
                    isError = state.userTypeError != null,
                    supportingText = state.userTypeError?.let { error ->
                        { Text(text = error, color = IPCA_Gold) }
                    },
                    enabled = !state.isLoading
                )
                ExposedDropdownMenu(
                    expanded = expandedUserType,
                    onDismissRequest = { expandedUserType = false },
                    modifier = Modifier
                        .background(IPCA_Green_Light)
                        .border(1.dp, IPCA_Border, RoundedCornerShape(4.dp))
                ) {
                    userTypes.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = Text_White) },
                            onClick = {
                                viewModel.onEvent(RegisterEvent.UserTypeChanged(option))
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

            // Contacto
            CustomLabel(text = "Contacto Telefónico")
            OutlinedTextField(
                value = state.contact,
                onValueChange = {
                    // Apenas permite números
                    if (it.all { char -> char.isDigit() } && it.length <= 9) {
                        viewModel.onEvent(RegisterEvent.ContactChanged(it))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("912345678", color = Text_Grey) },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                colors = ipcaInputColors(),
                singleLine = true,
                isError = state.contactError != null,
                supportingText = state.contactError?.let { error ->
                    { Text(text = error, color = IPCA_Gold) }
                },
                enabled = !state.isLoading
            )

            // Email
            CustomLabel(text = "Email")
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("seu.email@ipca.pt", color = Text_Grey) },
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

            // Password
            CustomLabel(text = "Senha")
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
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
                        viewModel.onEvent(RegisterEvent.Register)
                    }
                ),
                trailingIcon = {
                    val image = if (state.isPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(RegisterEvent.TogglePasswordVisibility) }
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

            // Botão de Registo
            Button(
                onClick = { viewModel.onEvent(RegisterEvent.Register) },
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
                        text = "Registar",
                        color = Text_White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
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
                    .clickable(enabled = !state.isLoading) {
                        navController.navigate(Screen.LoginScreen.route)
                    },
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_6a")
@Composable
fun RegisterPreview() {
    RegisterView(navController = rememberNavController())
}