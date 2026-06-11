package edu.ucne.vitaslot.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.vitaslot.presentation.Home.HomeScreen
import edu.ucne.vitaslot.presentation.consulta.ConsultaListScreen
import edu.ucne.vitaslot.presentation.consulta.ConsultaScreen
import edu.ucne.vitaslot.presentation.consulta.ConsultasViewModel
import edu.ucne.vitaslot.presentation.medico.MedicoListScreen
import edu.ucne.vitaslot.presentation.medico.MedicoScreen
import edu.ucne.vitaslot.presentation.medico.MedicosViewModel
import edu.ucne.vitaslot.presentation.paciente.PacienteListScreen
import edu.ucne.vitaslot.presentation.paciente.PacienteScreen
import edu.ucne.vitaslot.presentation.paciente.PacientesViewModel

@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    medicosViewModel: MedicosViewModel,
    pacientesViewModel: PacientesViewModel,
    consultasViewModel: ConsultasViewModel
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home
    ) {
        //inicio
        composable <Screen.Home> {
            HomeScreen(navController = navHostController)
        }

        //pantalla lista de medicos
        composable <Screen.MedicoList> {
            val medicos by medicosViewModel.medicos.collectAsState()

            MedicoListScreen(
                medicoList = medicos,
                onEdit = { id ->
                    navHostController.navigate(Screen.Medico(id ?: 0))
                },
                onDelete = { medico ->
                    medicosViewModel.deleteMedico(medico)
                }
            )

        }

        //pantalla formulario de medicos
        composable <Screen.Medico> { backStack ->
            val medicoId = backStack.toRoute<Screen.Medico>().medicoId
            MedicoScreen(
                medicoId = medicoId,
                viewModelMedico = medicosViewModel,
                navController = navHostController,
                function = { navHostController.popBackStack()}
            )
        }

        //pantalla lista de pacientes
        composable<Screen.PacienteList>{
            val pacientes by pacientesViewModel.pacientes.collectAsState()

            PacienteListScreen(
                pacienteList = pacientes,
                onEdit = { id ->
                    navHostController.navigate(Screen.Paciente(id ?: 0))
                },
                onDelete = { paciente ->
                    pacientesViewModel.deletePaciente(paciente)
                }
            )
        }

        //pantalla formulario de pacientes
        composable <Screen.Paciente>{ backStack ->
            val pacienteId = backStack.toRoute<Screen.Paciente>().pacienteId
            PacienteScreen(
                pacienteId = pacienteId,
                viewModel = pacientesViewModel,
                navController = navHostController,
                function = {navHostController.popBackStack()}
            )
        }

        //pantalla lista de consultas
        composable <Screen.ConsultaList>{
            val consultas by consultasViewModel.consultasS.collectAsState()

            ConsultaListScreen(
                consultaList = consultas,
                onEdit = { id ->
                    navHostController.navigate(Screen.Consulta(id ?: 0))
                },
                onDelete = {consulta ->
                    consultasViewModel.deleteConsulta(consulta)
                }
            )
        }

        //pantalla formulario consultas
        composable <Screen.Consulta>{ backStack ->
            val consultaId = backStack.toRoute<Screen.Consulta>().consultaId
            ConsultaScreen(
                consultaId = consultaId,
                viewModel = consultasViewModel,
                navController = navHostController,
                function = { navHostController.popBackStack()}
            )
        }
    }
}