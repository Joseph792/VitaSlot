package edu.ucne.vitaslot.presentation.medico

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.vitaslot.data.local.entities.MedicoEntity

@Composable
fun MedicoScreen(
    medicoId: Int? = null,
    viewModelMedico: MedicosViewModel,
    navController: NavController,
    function: () -> Unit

) {
    var nombre: String by remember { mutableStateOf("") }
    var apellido: String by remember { mutableStateOf("") }
    var direccion: String by remember { mutableStateOf("") }
    var telefono: String by remember { mutableStateOf("") }
    var correo: String by remember { mutableStateOf("") }
    var fechaNacimiento: String by remember { mutableStateOf("") }
    var especialidad: String by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf("") }
    var editando by remember {mutableStateOf<MedicoEntity?>(null)}

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
            Text("Registro de Medicos")

                    OutlinedTextField(
                        label = { Text(text = "Medico Id") },
                        value = editando?.medicoId?.toString() ?: "0",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false
                    )
                    OutlinedTextField(
                        label = { Text(text = "Nombre") },
                        value = nombre,
                        onValueChange = { nombre = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Apellido") },
                        value = apellido,
                        onValueChange = { apellido = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Direccion") },
                        value = direccion,
                        onValueChange = { direccion = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Telefono") },
                        value = telefono,
                        onValueChange = { telefono = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Correo") },
                        value = correo,
                        onValueChange = { correo = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Fecha de Nacimiento") },
                        value = fechaNacimiento,
                        onValueChange = { fechaNacimiento = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Especialidad") },
                        value = especialidad,
                        onValueChange = { especialidad = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text(text = "Nuevo")
                        }
                        val scope = rememberCoroutineScope()
                        OutlinedButton(
                            onClick = {
                                if (nombre.isBlank()) {
                                    errorMessage = "Nombre vacio"
                                    return@OutlinedButton
                                }

                                if (apellido.isBlank()) {
                                    errorMessage = "Apellido Vacio"
                                    return@OutlinedButton
                                }

                                if (direccion.isBlank()) {
                                    errorMessage = "Direccion Vacia"
                                    return@OutlinedButton
                                }

                                if (telefono.toString().isBlank()) {
                                    errorMessage = "Telefono Vacio"
                                    return@OutlinedButton
                                }

                                if (correo.isBlank()) {
                                    errorMessage = "Correo Vacio"
                                    return@OutlinedButton
                                }

                                if (fechaNacimiento.isBlank()) {
                                    errorMessage = "Fecha de Nacimiento Vacia"
                                    return@OutlinedButton
                                }

                                if (especialidad.isBlank()) {
                                    errorMessage = "Especialidad Vacia"
                                    return@OutlinedButton
                                }

                                //crear
                                viewModelMedico.saveMedico(
                                        MedicoEntity(
                                            medicoId = editando?.medicoId,
                                            nombre = nombre,
                                            apellido = apellido,
                                            direccion = direccion,
                                            telefono = telefono,
                                            correo = correo,
                                            fechaNacimiento = fechaNacimiento,
                                            especialidad = especialidad
                                        )
                                    )
                                    nombre = ""
                                    apellido = ""
                                    direccion = ""
                                    telefono = ""
                                    correo = ""
                                    fechaNacimiento = ""
                                    especialidad = ""
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