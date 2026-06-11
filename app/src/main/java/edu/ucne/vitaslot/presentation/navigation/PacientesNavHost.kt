package edu.ucne.vitaslot.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.vitaslot.data.local.entities.PacienteEntity
import edu.ucne.vitaslot.presentation.paciente.PacienteListScreen
import edu.ucne.vitaslot.presentation.paciente.PacienteScreen
import edu.ucne.vitaslot.presentation.paciente.PacientesViewModel

@Composable
fun PacientesNavHost(
    navHostController: NavHostController,
    pacienteList: List<PacienteEntity>,
    viewModel: PacientesViewModel,
    navController: NavController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PacienteList
    ) {
        composable<Screen.PacienteList> {
            PacienteListScreen(
                pacienteList = pacienteList,
                onEdit = { pacienteId ->
                    navHostController.navigate(Screen.Paciente(pacienteId))
                },
                onDelete = { paciente ->
                    viewModel.deletePaciente(paciente)
                }
            )
        }

        composable<Screen.Paciente>{ backStack ->
            val pacienteId  = backStack.toRoute<Screen.Paciente>().pacienteId
            PacienteScreen(
                pacienteId,
                viewModel,
                navController
            ) {
            }
        }
    }
}