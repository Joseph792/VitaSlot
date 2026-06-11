package edu.ucne.vitaslot.presentation.paciente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.vitaslot.data.local.entities.PacienteEntity
import edu.ucne.vitaslot.data.repository.PacientesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PacientesViewModel(
    private val pacientesRepository: PacientesRepository
): ViewModel() {
    fun savePaciente(paciente: PacienteEntity){
        viewModelScope.launch {
            pacientesRepository.save(paciente)
        }
    }

    suspend fun findPaciente(id: Int): PacienteEntity? {
        return pacientesRepository.find(id)
    }

    fun deletePaciente(paciente: PacienteEntity){
        viewModelScope.launch {
            pacientesRepository.delete(paciente)
        }
    }

    // Exponer los pacientes como StateFlow
    val pacientes: StateFlow<List<PacienteEntity>> = pacientesRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

}