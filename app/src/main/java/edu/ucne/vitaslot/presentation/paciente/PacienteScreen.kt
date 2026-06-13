package edu.ucne.vitaslot.presentation.paciente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.vitaslot.data.local.entities.PacienteEntity

private val TopBarBlue = Color(0xFF1976D2)
private val FieldFocusedBorder = Color(0xFF1976D2)
private val FieldUnfocusedBorder = Color(0xFFBDBDBD)
private val ErrorRed = Color(0xFFD32F2F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacienteScreen(
    pacienteId: Int? = null,
    viewModel: PacientesViewModel,
    navController: NavController,
    function: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var editando by remember { mutableStateOf<PacienteEntity?>(null) }

    LaunchedEffect(pacienteId) {
        if (pacienteId != null && pacienteId > 0) {
            val paciente = viewModel.findPaciente(pacienteId)
            paciente?.let {
                editando = it
                nombre = it.nombre
                apellido = it.apellido
                direccion = it.direccion
                telefono = it.telefono
                correo = it.correo
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (editando != null) "Editar Paciente" else "Nuevo Paciente",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TopBarBlue)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Registro de Pacientes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TopBarBlue
                    )

                    VitaOutlinedTextField(
                        value = editando?.pacienteId?.toString() ?: "0",
                        onValueChange = {},
                        label = "Paciente ID",
                        enabled = false
                    )
                    VitaOutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
                    VitaOutlinedTextField(value = apellido, onValueChange = { apellido = it }, label = "Apellido")
                    VitaOutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = "Dirección")
                    VitaOutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = "Teléfono")
                    VitaOutlinedTextField(value = correo, onValueChange = { correo = it }, label = "Correo")

                    errorMessage?.let {
                        Text(text = it, color = ErrorRed, fontSize = 13.sp)
                    }

                    Button(
                        onClick = {
                            if (nombre.isBlank())    { errorMessage = "Nombre vacío"; return@Button }
                            if (apellido.isBlank())  { errorMessage = "Apellido vacío"; return@Button }
                            if (direccion.isBlank()) { errorMessage = "Dirección vacía"; return@Button }
                            if (telefono.isBlank())  { errorMessage = "Teléfono vacío"; return@Button }
                            if (correo.isBlank())    { errorMessage = "Correo vacío"; return@Button }

                            viewModel.savePaciente(
                                PacienteEntity(
                                    pacienteId = editando?.pacienteId,
                                    nombre = nombre, apellido = apellido,
                                    direccion = direccion, telefono = telefono,
                                    correo = correo
                                )
                            )
                            nombre = ""; apellido = ""; direccion = ""
                            telefono = ""; correo = ""
                            errorMessage = null; editando = null
                            navController.navigateUp()
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TopBarBlue)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text("Guardar", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
private fun VitaOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = !enabled,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldFocusedBorder,
            unfocusedBorderColor = FieldUnfocusedBorder,
            focusedLabelColor = FieldFocusedBorder,
            disabledBorderColor = Color(0xFFE0E0E0),
            disabledLabelColor = Color(0xFF9E9E9E),
            disabledTextColor = Color(0xFF9E9E9E)
        )
    )
}