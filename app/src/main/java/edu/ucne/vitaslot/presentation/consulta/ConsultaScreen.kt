package edu.ucne.vitaslot.presentation.consulta

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import java.util.Date

private val TopBarBlue = Color(0xFF1976D2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaScreen(
    consultaId: Int? = null,
    viewModel: ConsultasViewModel,
    navController: NavController,
    function: () -> Unit
) {
    var fecha by remember { mutableStateOf(Date()) }
    var hora by remember { mutableStateOf(Date()) }
    var pacienteId by remember { mutableStateOf(0) }
    var medicoId by remember { mutableStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>("") }
    var editando by remember { mutableStateOf<ConsultaEntity?>(null) }

    val pacientes by viewModel.ListaPacientes.collectAsState()
    val medicos by viewModel.ListaMedicos.collectAsState()

    LaunchedEffect(consultaId) {
        if (consultaId != null && consultaId > 0) {
            val consulta = viewModel.findConsulta(consultaId)
            consulta?.let {
                editando = it
                fecha = it.fecha
                hora = it.hora
                medicoId = it.medicoId
                pacienteId = it.pacienteId
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (editando != null) "Editar Consulta" else "Nueva Consulta",
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
                        text = "Registro de Consultas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TopBarBlue
                    )

                    VitaTextField(
                        value = editando?.consultaId?.toString() ?: "0",
                        onValueChange = {},
                        label = "Consulta ID",
                        enabled = false
                    )

                    // Dropdown Paciente
                    var expandedPaciente by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedPaciente,
                        onExpandedChange = { expandedPaciente = !expandedPaciente }
                    ) {
                        OutlinedTextField(
                            value = pacientes.find { it.pacienteId == pacienteId }?.nombre ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Paciente asignado") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPaciente) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF1976D2),
                                unfocusedBorderColor = Color(0xFFBDBDBD),
                                focusedLabelColor = Color(0xFF1976D2)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedPaciente,
                            onDismissRequest = { expandedPaciente = false }
                        ) {
                            pacientes.forEach { paciente ->
                                DropdownMenuItem(
                                    text = { Text(paciente.nombre) },
                                    onClick = {
                                        pacienteId = paciente.pacienteId!!
                                        expandedPaciente = false
                                    }
                                )
                            }
                        }
                    }

                    // Dropdown Médico
                    var expandedMedico by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedMedico,
                        onExpandedChange = { expandedMedico = !expandedMedico }
                    ) {
                        OutlinedTextField(
                            value = medicos.find { it.medicoId == medicoId }?.nombre ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Médico asignado") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMedico) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF1976D2),
                                unfocusedBorderColor = Color(0xFFBDBDBD),
                                focusedLabelColor = Color(0xFF1976D2)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedMedico,
                            onDismissRequest = { expandedMedico = false }
                        ) {
                            medicos.forEach { medico ->
                                DropdownMenuItem(
                                    text = { Text(medico.nombre) },
                                    onClick = {
                                        medicoId = medico.medicoId!!
                                        expandedMedico = false
                                    }
                                )
                            }
                        }
                    }

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
                        pacienteId = 0; medicoId = 0
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
                        if (pacienteId <= 0) { errorMessage = "Seleccione un Paciente"; return@Button }
                        if (medicoId <= 0)   { errorMessage = "Seleccione un Médico"; return@Button }

                        viewModel.saveConsulta(
                            ConsultaEntity(
                                consultaId = editando?.consultaId,
                                fecha = fecha, hora = hora,
                                medicoId = medicoId, pacienteId = pacienteId
                            )
                        )
                        pacienteId = 0; medicoId = 0
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