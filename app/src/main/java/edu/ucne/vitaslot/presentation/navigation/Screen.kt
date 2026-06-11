package edu.ucne.vitaslot.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data object MedicoList : Screen()
    @Serializable
    data class Medico(val medicoId: Int?) : Screen()
    @Serializable
    data object PacienteList : Screen()
    @Serializable
    data class Paciente(val pacienteId: Int?) : Screen()
    @Serializable
    data object ConsultaList : Screen()
    @Serializable
    data class Consulta(val consultaId: Int?) : Screen()
}
