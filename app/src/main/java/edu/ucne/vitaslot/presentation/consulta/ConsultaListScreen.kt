package edu.ucne.vitaslot.presentation.consulta

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
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaListScreen(
    consultaList: List<ConsultaEntity>,
    onEdit: (Int?) -> Unit,
    onDelete: (ConsultaEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Consultas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEdit(0) }) {
                Icon(Icons.Filled.Add, "Agregar nueva")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(consultaList) { consulta ->
                    ConsultaRow(consulta, { onEdit(consulta.consultaId) }, { onDelete(consulta) })
                }
            }
        }
    }
}

@Composable
private fun ConsultaRow(
    consulta: ConsultaEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(modifier = Modifier.weight(1f),
            text = consulta.consultaId.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = consulta.fecha.toFormattedString(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = consulta.medicoId.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.weight(2f),
            text = consulta.pacienteId.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
        }

    }
    HorizontalDivider()
}
fun Date.toFormattedString(): String {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(this)
}