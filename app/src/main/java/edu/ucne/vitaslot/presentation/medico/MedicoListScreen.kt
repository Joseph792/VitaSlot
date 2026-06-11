package edu.ucne.vitaslot.presentation.medico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.vitaslot.data.local.entities.MedicoEntity

// ── Paleta de avatares con gradientes (igual al preview HTML) ──────────────────
private data class AvatarStyle(
    val gradientStart: Color,
    val gradientEnd: Color,
    val chipBg: Color,
    val chipText: Color
)

private val avatarStyles = listOf(
    AvatarStyle(Color(0xFF1976D2), Color(0xFF42A5F5), Color(0xFFE3F2FD), Color(0xFF1565C0)), // Azul
    AvatarStyle(Color(0xFF00796B), Color(0xFF26C6DA), Color(0xFFE0F2F1), Color(0xFF004D40)), // Teal
    AvatarStyle(Color(0xFF6A1B9A), Color(0xFFAB47BC), Color(0xFFF3E5F5), Color(0xFF4A148C)), // Púrpura
    AvatarStyle(Color(0xFFE65100), Color(0xFFFF8A65), Color(0xFFFBE9E7), Color(0xFFBF360C)), // Naranja
)

// ── TopBar azul (igual al preview) ────────────────────────────────────────────
private val TopBarBlue = Color(0xFF1976D2)
private val TopBarDark = Color(0xFF1565C0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicoListScreen(
    medicoList: List<MedicoEntity>,
    onEdit: (Int?) -> Unit,
    onDelete: (MedicoEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = medicoList.filter { medico ->
        if (searchQuery.isBlank()) true
        else medico.nombre.contains(searchQuery, ignoreCase = true)
                || medico.apellido.contains(searchQuery, ignoreCase = true)
                || medico.especialidad.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Médicos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                actions = {
                    Text(
                        text = "${filteredList.size} médicos",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBarBlue,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEdit(0) },
                shape = RoundedCornerShape(16.dp),
                containerColor = TopBarBlue,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar médico",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Barra de búsqueda ────────────────────────────────────────────
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        "Buscar por nombre o especialidad...",
                        fontSize = 14.sp,
                        color = Color(0xFF90A4AE)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = TopBarBlue
                )
            )

            // ── Etiqueta de sección ──────────────────────────────────────────
            Text(
                text = "${filteredList.size} médicos registrados".uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF90A4AE),
                letterSpacing = 1.2.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ── Lista de tarjetas ────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 4.dp
                )
            ) {
                items(filteredList) { medico ->
                    MedicoCard(
                        medico = medico,
                        styleIndex = medicoList.indexOf(medico) % avatarStyles.size,
                        onEdit = { onEdit(medico.medicoId) },
                        onDelete = { onDelete(medico) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

// ── Tarjeta de médico ──────────────────────────────────────────────────────────
@Composable
private fun MedicoCard(
    medico: MedicoEntity,
    styleIndex: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val style = avatarStyles[styleIndex]
    val initials = buildString {
        append(medico.nombre.firstOrNull()?.uppercaseChar() ?: "")
        append(medico.apellido.firstOrNull()?.uppercaseChar() ?: "")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ── Avatar con gradiente ─────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(style.gradientStart, style.gradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = (-1).sp
                )
            }

            // ── Información ──────────────────────────────────────────────────
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${medico.nombre} ${medico.apellido}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = medico.especialidad,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TopBarBlue,
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                MetaItem(icon = Icons.Outlined.Phone,        text = medico.telefono)
                MetaItem(icon = Icons.Outlined.Email,        text = medico.correo)
                MetaItem(icon = Icons.Outlined.LocationOn,   text = medico.direccion)
                MetaItem(icon = Icons.Outlined.CalendarMonth,text = medico.fechaNacimiento)

                Spacer(modifier = Modifier.height(6.dp))

                // Chip de especialidad
                EspecialidadChip(
                    label = medico.especialidad,
                    bgColor = style.chipBg,
                    textColor = style.chipText
                )
            }

            // ── Botones de acción ────────────────────────────────────────────
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundActionButton(
                    icon = Icons.Default.Edit,
                    description = "Editar",
                    iconColor = Color(0xFF1976D2),
                    bgColor = Color(0xFFE3F2FD),
                    onClick = onEdit
                )
                RoundActionButton(
                    icon = Icons.Default.Delete,
                    description = "Eliminar",
                    iconColor = Color(0xFFD32F2F),
                    bgColor = Color(0xFFFFEBEE),
                    onClick = onDelete
                )
            }
        }
    }
}

// ── Chip de especialidad ───────────────────────────────────────────────────────
@Composable
private fun EspecialidadChip(
    label: String,
    bgColor: Color,
    textColor: Color
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

// ── Fila de metadato ───────────────────────────────────────────────────────────
@Composable
private fun MetaItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(bottom = 2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF757575),
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = text,
            fontSize = 11.5.sp,
            color = Color(0xFF757575),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ── Botón circular de acción ───────────────────────────────────────────────────
@Composable
private fun RoundActionButton(
    icon: ImageVector,
    description: String,
    iconColor: Color,
    bgColor: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(bgColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = iconColor,
            modifier = Modifier.size(18.dp)
        )
    }
}