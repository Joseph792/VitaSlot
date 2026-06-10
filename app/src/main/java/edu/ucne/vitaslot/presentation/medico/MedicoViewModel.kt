package edu.ucne.vitaslot.presentation.medico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.vitaslot.data.local.entities.MedicoEntity
import edu.ucne.vitaslot.data.repository.MedicosRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MedicosViewModel(
    private val medicosRepository: MedicosRepository
): ViewModel() {
    fun saveMedico(medico: MedicoEntity) {
        viewModelScope.launch {
            medicosRepository.save(medico)
        }
    }

    suspend fun findMedico(id: Int): MedicoEntity? {
        return medicosRepository.find(id)
    }

    fun deleteMedico(medico: MedicoEntity) {
        viewModelScope.launch {
            medicosRepository.delete(medico)
        }
    }

    val medicos: StateFlow<List<MedicoEntity>> = medicosRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}