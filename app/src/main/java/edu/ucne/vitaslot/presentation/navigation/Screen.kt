package edu.ucne.vitaslot.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object MedicoList : Screen()
    @Serializable
    data class Medico(val medicoId: Int?) : Screen()
    @Serializable
    data object Home : Screen()
}