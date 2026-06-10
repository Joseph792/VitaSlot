package edu.ucne.vitaslot.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.vitaslot.presentation.Home.HomeScreen
import edu.ucne.vitaslot.presentation.medico.MedicoListScreen
import edu.ucne.vitaslot.presentation.medico.MedicoScreen
import edu.ucne.vitaslot.presentation.medico.MedicosViewModel

@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    medicosViewModel: MedicosViewModel
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

        /*//pantalla lista de tecnicos
        composable<Screen.TecnicoList>{
            val tecnicos by tecnicosViewModel.tecnicos.collectAsState()

            TecnicoListScreen(
                tecnicoList = tecnicos,
                onEdit = { id ->
                    navHostController.navigate(Screen.Tecnico(id ?: 0))
                },
                onDelete = { tecnico ->
                    tecnicosViewModel.deleteTecnico(tecnico)
                }
            )
        }

        //pantalla formulario de tecnico
        composable <Screen.Tecnico>{ backStack ->
            val tecnicoId = backStack.toRoute<Screen.Tecnico>().tecnicoId
            TecnicoScreen(
                tecnicoId = tecnicoId,
                viewModel = tecnicosViewModel,
                navController = navHostController,
                function = {navHostController.popBackStack()}
            )
        }

        //pantalla lista de tickets
        composable <Screen.TicketList>{
            val tickets by ticketsViewModel.ticketsS.collectAsState()

            TicketListScreen(
                ticketList = tickets,
                onEdit = { id ->
                    navHostController.navigate(Screen.Ticket(id ?: 0))
                },
                onDelete = {ticket ->
                    ticketsViewModel.deleteTicket(ticket)
                }
            )
        }

        //pantalla formulario tickets
        composable <Screen.Ticket>{ backStack ->
            val ticketId = backStack.toRoute<Screen.Ticket>().ticketId
            TicketScreen(
                ticketId = ticketId,
                viewModel = ticketsViewModel,
                navController = navHostController,
                function = { navHostController.popBackStack()}
            )
        }*/
    }
}