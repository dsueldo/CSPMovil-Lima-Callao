package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.UserItem
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark

@Composable
fun AdminBody(
    users: List<ProfileUiState>,
    searchQuery: String,
    isLoading: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onUpdateUser: (uid: String, specialized: String, condition: String, payLastPeriod: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedUser by remember { mutableStateOf<ProfileUiState?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Buscar por nombre, DNI o código") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Limpiar")
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Red_Dark,
                focusedLabelColor = Red_Dark,
                cursorColor = Red_Dark
            ),
            shape = MaterialTheme.shapes.medium
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isLoading && users.isEmpty()) {
                items(10) {
                    UserItemShimmer()
                }
            } else {
                items(
                    items = users,
                    key = { it.id }
                ) { user ->
                    UserItem(
                        user = user,
                        onClick = {
                            selectedUser = user
                            showEditDialog = true
                        }
                    )
                }

                if (users.isEmpty() && searchQuery.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No se encontraron usuarios", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog && selectedUser != null) {
        UserEditDialog(
            user = selectedUser!!,
            onDismiss = { showEditDialog = false },
            onConfirm = { spec, cond, pay ->
                onUpdateUser(selectedUser!!.id, spec, cond, pay)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun UserEditDialog(
    user: ProfileUiState,
    onDismiss: () -> Unit,
    onConfirm: (specialized: String, condition: String, payLastPeriod: String) -> Unit
) {
    var specialized by remember { mutableStateOf(user.specialized) }
    var condition by remember { mutableStateOf(user.condition) }
    var payLastPeriod by remember { mutableStateOf(user.payLastPeriod) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Info Profesional", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("${user.name} ${user.lastName}", style = MaterialTheme.typography.bodyLarge)
                
                OutlinedTextField(
                    value = specialized,
                    onValueChange = { specialized = it },
                    label = { Text("Especialidad") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark,
                        cursorColor = Red_Dark
                    )
                )
                
                OutlinedTextField(
                    value = condition,
                    onValueChange = { condition = it },
                    label = { Text("Condición") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark,
                        cursorColor = Red_Dark
                    )
                )
                
                OutlinedTextField(
                    value = payLastPeriod,
                    onValueChange = { payLastPeriod = it },
                    label = { Text("Último Periodo Pagado") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark,
                        cursorColor = Red_Dark
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(specialized, condition, payLastPeriod) },
                colors = ButtonDefaults.buttonColors(containerColor = Red_Dark)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Red_Dark)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AdminBodyPreview() {
    AdminBody(
        users = listOf(
            ProfileUiState(name = "Christian", lastName = "Quispe", dni = "12345678"),
            ProfileUiState(name = "Juan", lastName = "Perez", dni = "87654321")
        ),
        searchQuery = "",
        isLoading = false,
        onSearchQueryChange = {},
        onUpdateUser = { _, _, _, _ -> }
    )
}
