package edu.ucne.vitaslot.presentation.medico

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.vitaslot.data.local.entities.MedicoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicoListScreen(
    medicoList: List<MedicoEntity>,
    onEdit: (Int?) -> Unit,
    onDelete: (MedicoEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Médicos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEdit(0) }) {
                Icon(Icons.Filled.Add, "Agregar nuevo")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(medicoList) { medico ->
                    MedicoRow(medico, { onEdit(medico.medicoId) }, { onDelete(medico) })
                }
            }
        }
    }
}

@Composable
private fun MedicoRow(
    medico: MedicoEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = medico.medicoId.toString(),
            color = Color.Black
            )
        Text(
            modifier = Modifier.weight(2f),
            text = medico.nombre,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = medico.apellido,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = medico.direccion,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(modifier = Modifier.weight(1f),
            text = medico.telefono,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = medico.correo,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = medico.fechaNacimiento,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = medico.especialidad,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Edit,
                contentDescription = "Editar",
                tint = MaterialTheme.colorScheme.primary)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = MaterialTheme.colorScheme.error)
        }
    }
    HorizontalDivider()
}