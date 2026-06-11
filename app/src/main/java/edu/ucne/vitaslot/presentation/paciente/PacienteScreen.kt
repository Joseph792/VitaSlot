package edu.ucne.vitaslot.presentation.paciente

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.vitaslot.data.local.entities.PacienteEntity

@Composable
fun PacienteScreen(
    pacienteId: Int? = null,
    viewModel: PacientesViewModel,
    navController: NavController,
    function: () -> Unit
){
    var nombre: String by remember { mutableStateOf("") }
    var apellido: String by remember { mutableStateOf("") }
    var direccion: String by remember { mutableStateOf("") }
    var telefono: String by remember { mutableStateOf("") }
    var correo: String by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf("") }
    var editando by remember { mutableStateOf<PacienteEntity?>(null) }

    LaunchedEffect(pacienteId) {
        if (pacienteId != null && pacienteId > 0){
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
    Scaffold { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                if (navController != null){
                    IconButton(
                        onClick = {navController.popBackStack()},
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "volver")
                    }
                }
            }
            ElevatedCard (
                modifier = Modifier.fillMaxWidth()
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    Spacer(modifier = Modifier.height(32.dp))
                    Text("Registro de Pacientes")

                    OutlinedTextField(
                        value = editando?.pacienteId?.toString() ?: "0",
                        onValueChange = {},
                        label = {Text("Paciente Id")},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false
                    )
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {nombre = it},
                        label = {Text("Nombre")},
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = {apellido = it},
                        label = {Text("Apellido")},
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = {direccion = it},
                        label = {Text("Direccion")},
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = {telefono = it},
                        label = {Text("Telefono")},
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = correo,
                        onValueChange = {correo = it},
                        label = {Text("Correo")},
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        OutlinedButton(
                            onClick = {
                                if (nombre.isBlank()){
                                    errorMessage = "Nombre vacio"
                                    return@OutlinedButton
                                }
                                if (apellido.isBlank()){
                                    errorMessage = "Apellido Vacio"
                                    return@OutlinedButton
                                }
                                if (direccion.isBlank()){
                                    errorMessage = "Direccion Vacia"
                                    return@OutlinedButton
                                }
                                if (telefono.toString().isBlank()){
                                    errorMessage = "Telefono Vacio"
                                    return@OutlinedButton
                                }
                                if (correo.isBlank()){
                                    errorMessage = "Correo Vacio"
                                    return@OutlinedButton
                                }

                                viewModel.savePaciente(
                                    PacienteEntity(
                                        pacienteId = editando?.pacienteId,
                                        nombre = nombre,
                                        apellido = apellido,
                                        direccion = direccion,
                                        telefono = telefono,
                                        correo = correo
                                    )
                                )
                                nombre = ""
                                apellido = ""
                                direccion = ""
                                telefono = ""
                                correo = ""
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