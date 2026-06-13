package edu.ucne.vitaslot.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.vitaslot.presentation.navigation.Screen

private val TopBarBlue = Color(0xFF1976D2)

private data class MenuOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val gradientStart: Color,
    val gradientEnd: Color,
    val route: Any
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val options = listOf(
        MenuOption(
            title = "Médicos",
            subtitle = "Gestionar registro de médicos",
            icon = Icons.Outlined.LocalHospital,
            gradientStart = Color(0xFF1976D2),
            gradientEnd = Color(0xFF42A5F5),
            route = Screen.MedicoList
        ),
        MenuOption(
            title = "Pacientes",
            subtitle = "Gestionar registro de pacientes",
            icon = Icons.Outlined.Person,
            gradientStart = Color(0xFF00796B),
            gradientEnd = Color(0xFF26C6DA),
            route = Screen.PacienteList
        ),
        MenuOption(
            title = "Consultas",
            subtitle = "Gestionar registro de consultas",
            icon = Icons.Outlined.CalendarMonth,
            gradientStart = Color(0xFF6A1B9A),
            gradientEnd = Color(0xFFAB47BC),
            route = Screen.ConsultaList
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "VitaSlot",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBarBlue
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Menú principal".uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF90A4AE),
                letterSpacing = 1.2.sp,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
            )

            options.forEach { option ->
                HomeMenuCard(
                    option = option,
                    onClick = { navController.navigate(option.route) }
                )
            }
        }
    }
}

@Composable
private fun HomeMenuCard(
    option: MenuOption,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ícono con gradiente
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(option.gradientStart, option.gradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = option.title,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Texto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = option.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = option.subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF757575)
                )
            }

            // Flecha
            Text(
                text = "›",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF90A4AE)
            )
        }
    }
}