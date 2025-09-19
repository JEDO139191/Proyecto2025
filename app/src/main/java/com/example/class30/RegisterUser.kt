package com.example.class30.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.class30.R
import com.example.class30.ui.theme.CLASS30Theme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun RegisterUser(
    onRegisterSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var repeatPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val auth = Firebase.auth

    // Validaciones
    val isEmailValid = email.contains("@") && email.isNotBlank()
    val isPasswordValid = password.length >= 6
    val doPasswordsMatch = password == repeatPassword && repeatPassword.isNotBlank()
    val isNameValid = name.isNotBlank()
    val isLastNameValid = lastName.isNotBlank()
    val isAgeValid = age.isNotBlank() && age.toIntOrNull() != null && age.toIntOrNull()!! > 0
    val isFormValid = isEmailValid && isPasswordValid && doPasswordsMatch &&
            isNameValid && isLastNameValid && isAgeValid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.Register_User),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campos de registro
                    RegisterTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = stringResource(id = R.string.Email),
                        keyboardType = KeyboardType.Email,
                        isError = email.isNotBlank() && !isEmailValid
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RegisterTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = stringResource(id = R.string.Password),
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible },
                        isError = password.isNotBlank() && !isPasswordValid
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RegisterTextField(
                        value = repeatPassword,
                        onValueChange = { repeatPassword = it },
                        label = stringResource(id = R.string.Repeat_Password),
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = repeatPasswordVisible,
                        onPasswordToggle = { repeatPasswordVisible = !repeatPasswordVisible },
                        isError = repeatPassword.isNotBlank() && !doPasswordsMatch
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RegisterTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = stringResource(id = R.string.Name),
                        keyboardType = KeyboardType.Text,
                        isError = name.isNotBlank() && !isNameValid
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RegisterTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = stringResource(id = R.string.Last_name),
                        keyboardType = KeyboardType.Text,
                        isError = lastName.isNotBlank() && !isLastNameValid
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RegisterTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = stringResource(id = R.string.Age),
                        keyboardType = KeyboardType.Number,
                        isError = age.isNotBlank() && !isAgeValid
                    )

                    // Mensajes de error
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Botón de registro
                    Button(
                        onClick = {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        errorMessage = null
                                        onRegisterSuccess()
                                    } else {
                                        errorMessage = task.exception?.message
                                    }
                                }
                        },
                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.Register),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isFormValid) Color.Black else Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para volver al login
                    TextButton(onClick = onBackClick) {
                        Text(
                            text = "¿Ya tienes cuenta? Inicia sesión",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = if (isPassword) {
            {
                val visibilityIcon =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { onPasswordToggle?.invoke() }) {
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Color.Gray
                    )
                }
            }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            cursorColor = Color.Black,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Red,
            focusedContainerColor = Color.White.copy(alpha = 0.9f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
            disabledContainerColor = Color.White.copy(alpha = 0.7f),
            errorContainerColor = Color.White.copy(alpha = 0.7f)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterUserPreview() {
    CLASS30Theme {
        RegisterUser()
    }
}
