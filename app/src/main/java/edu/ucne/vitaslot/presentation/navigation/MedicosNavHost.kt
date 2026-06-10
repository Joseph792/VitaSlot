package edu.ucne.vitaslot.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.vitaslot.data.local.entities.MedicoEntity
import edu.ucne.vitaslot.presentation.medico.MedicoListScreen
import edu.ucne.vitaslot.presentation.medico.MedicoScreen
import edu.ucne.vitaslot.presentation.medico.MedicosViewModel

@Composable
fun MedicosNavHost(
    navHostController: NavHostController,
    medicoList: List<MedicoEntity>,
    viewModel: MedicosViewModel,
    navController: NavController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MedicoList
    ) {
        composable<Screen.MedicoList> {
            MedicoListScreen(
                medicoList = medicoList,
                onEdit = { medicoId ->
                    navHostController.navigate(Screen.Medico(medicoId))
                },
                onDelete = { medico ->
                    viewModel.deleteMedico(medico)
                }
            )
        }

        composable<Screen.Medico>{ backStack ->
            val medicoId  = backStack.toRoute<Screen.Medico>().medicoId
            MedicoScreen(
                medicoId,
                viewModel,
                navController
            ) {
            }
        }
    }
}