package edu.ucne.vitaslot.presentation.medico

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.vitaslot.data.local.entities.MedicoEntity

private val TopBarBlue = Color(0xFF1976D2)
private val FieldBorderBlue = Color(0xFF1976D2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicoScreen(
    medicoId: Int? = null,
    viewModelMedico: MedicosViewModel,
    navController: NavController,
    function: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>("") }
    var editando by remember { mutableStateOf<MedicoEntity?>(null) }

    LaunchedEffect(medicoId) {
        if (medicoId != null && medicoId > 0) {
            val medico = viewModelMedico.findMedico(medicoId)
            medico?.let {
                editando = it
                nombre = it.nombre
                apellido = it.apellido
                direccion = it.direccion
                telefono = it.telefono
                correo = it.correo
                fechaNacimiento = it.fechaNacimiento
                especialidad = it.especialidad
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (editando != null) "Editar Médico" else "Nuevo Médico",
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        text = "Registro de Médicos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TopBarBlue
                    )

                    VitaTextField(
                        value = editando?.medicoId?.toString() ?: "0",
                        onValueChange = {},
                        label = "Médico ID",
                        enabled = false
                    )
                    VitaTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
                    VitaTextField(value = apellido, onValueChange = { apellido = it }, label = "Apellido")
                    VitaTextField(value = direccion, onValueChange = { direccion = it }, label = "Dirección")
                    VitaTextField(value = telefono, onValueChange = { telefono = it }, label = "Teléfono")
                    VitaTextField(value = correo, onValueChange = { correo = it }, label = "Correo")
                    VitaTextField(value = fechaNacimiento, onValueChange = { fechaNacimiento = it }, label = "Fecha de Nacimiento")
                    VitaTextField(value = especialidad, onValueChange = { especialidad = it }, label = "Especialidad")

                    errorMessage?.let {
                        if (it.isNotBlank()) Text(text = it, color = Color.Red, fontSize = 13.sp)
                    }
                }
            }

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        nombre = ""; apellido = ""; direccion = ""
                        telefono = ""; correo = ""; fechaNacimiento = ""; especialidad = ""
                        errorMessage = null; editando = null
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TopBarBlue),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, TopBarBlue)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text("Nuevo", fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = {
                        if (nombre.isBlank()) { errorMessage = "Nombre vacío"; return@Button }
                        if (apellido.isBlank()) { errorMessage = "Apellido vacío"; return@Button }
                        if (direccion.isBlank()) { errorMessage = "Dirección vacía"; return@Button }
                        if (telefono.isBlank()) { errorMessage = "Teléfono vacío"; return@Button }
                        if (correo.isBlank()) { errorMessage = "Correo vacío"; return@Button }
                        if (fechaNacimiento.isBlank()) { errorMessage = "Fecha de nacimiento vacía"; return@Button }
                        if (especialidad.isBlank()) { errorMessage = "Especialidad vacía"; return@Button }

                        viewModelMedico.saveMedico(
                            MedicoEntity(
                                medicoId = editando?.medicoId,
                                nombre = nombre, apellido = apellido,
                                direccion = direccion, telefono = telefono,
                                correo = correo, fechaNacimiento = fechaNacimiento,
                                especialidad = especialidad
                            )
                        )
                        errorMessage = null; editando = null
                        navController.navigateUp()
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TopBarBlue)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text("Guardar", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
private fun VitaTextField(
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
            focusedBorderColor = Color(0xFF1976D2),
            unfocusedBorderColor = Color(0xFFBDBDBD),
            focusedLabelColor = Color(0xFF1976D2),
            disabledBorderColor = Color(0xFFE0E0E0),
            disabledTextColor = Color(0xFF9E9E9E),
            disabledLabelColor = Color(0xFF9E9E9E)
        )
    )
}