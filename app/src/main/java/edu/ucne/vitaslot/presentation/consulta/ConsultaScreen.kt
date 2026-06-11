package edu.ucne.vitaslot.presentation.consulta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaScreen(
    consultaId: Int? = null,
    viewModel: ConsultasViewModel,
    navController: NavController,
    function: () -> Unit
) {
    var fecha: Date by remember { mutableStateOf(Date()) }
    var hora: Date by remember { mutableStateOf(Date()) }
    var pacienteId: Int by remember { mutableStateOf(0) }
    var medicoId: Int by remember { mutableStateOf(0) }
    var errorMessage: String? by remember { mutableStateOf("") }
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

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (navController != null){
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "volver")
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text("Registro de Consultas")

                    OutlinedTextField(
                        value = editando?.consultaId?.toString() ?: "0",
                        onValueChange = {},
                        label = { Text("Consulta Id") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false
                    )

                    //paciente
                    Spacer(modifier = Modifier.height(8.dp))

                    var expandedPaciente by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expandedPaciente,
                        onExpandedChange = { expandedPaciente = !expandedPaciente }
                    ) {
                        OutlinedTextField(
                            value = pacientes.find { it.pacienteId == pacienteId }?.nombre ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Paciente Asignado") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPaciente) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
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

                    /*OutlinedTextField(
                        value = fecha.toString(),
                        onValueChange = { fecha = it },
                        label = { Text("Fecha") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.Blue
                        )
                    )*/

                    //medico
                    Spacer(modifier = Modifier.height(8.dp))

                    var expandedMedico by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expandedMedico,
                        onExpandedChange = { expandedMedico = !expandedMedico }
                    ) {
                        OutlinedTextField(
                            value = medicos.find { it.medicoId == medicoId }?.nombre ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Médico Asignado") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMedico) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
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



                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {

                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Blue
                            ),
                            border = BorderStroke(1.dp, Color.Blue),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text("Nuevo")
                        }
                        OutlinedButton(
                            onClick = {
                                if (fecha.toString().isBlank()) {
                                    errorMessage = "Fecha vacia"
                                    return@OutlinedButton
                                }
                                if (pacienteId <= 0) {
                                    errorMessage = "Seleccione un Paciente"
                                    return@OutlinedButton
                                }
                                if (medicoId <= 0) {
                                    errorMessage = "Seleccione un Medico"
                                    return@OutlinedButton
                                }

                                //crear
                                viewModel.saveConsulta(
                                    ConsultaEntity(
                                        consultaId = editando?.consultaId,
                                        fecha = fecha,
                                        hora = hora,
                                        medicoId = medicoId,
                                        pacienteId = pacienteId
                                    )
                                )
                                errorMessage = null
                                editando = null

                                navController.navigateUp()
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Blue
                            ),
                            border = BorderStroke(1.dp, Color.Blue),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }
}