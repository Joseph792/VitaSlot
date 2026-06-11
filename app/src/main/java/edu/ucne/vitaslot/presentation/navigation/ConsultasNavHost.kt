package edu.ucne.vitaslot.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import edu.ucne.vitaslot.presentation.consulta.ConsultaListScreen
import edu.ucne.vitaslot.presentation.consulta.ConsultaScreen
import edu.ucne.vitaslot.presentation.consulta.ConsultasViewModel

@Composable
fun ConsultasNavHost(
    navHostController: NavHostController,
    consultaList: List<ConsultaEntity>,
    viewModel: ConsultasViewModel,
    navController: NavController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ConsultaList
    ) {
        composable<Screen.ConsultaList> {
            ConsultaListScreen(
                consultaList = consultaList,
                onEdit = { consultaId ->
                    navHostController.navigate(Screen.Consulta(consultaId))
                },
                onDelete = { consulta ->
                    viewModel.deleteConsulta(consulta)
                }
            )
        }

        composable<Screen.Consulta>{ backStack ->
            val consultaId  = backStack.toRoute<Screen.Consulta>().consultaId
            ConsultaScreen(
                consultaId,
                viewModel,
                navController
            ) {
            }
        }
    }
}