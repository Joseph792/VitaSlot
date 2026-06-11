package edu.ucne.vitaslot.presentation.consulta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import edu.ucne.vitaslot.data.repository.ConsultasRepository
import edu.ucne.vitaslot.data.repository.MedicosRepository
import edu.ucne.vitaslot.data.repository.PacientesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConsultasViewModel(
    private val consultasRepository: ConsultasRepository,
    private val medicosRepository: MedicosRepository,
    private  val pacientesRepository: PacientesRepository

): ViewModel() {
    private val consultaList = MutableStateFlow<List<ConsultaEntity>>(emptyList())
    val consultas: StateFlow<List<ConsultaEntity>> = consultaList.asStateFlow()

    //paciente
    val ListaPacientes = pacientesRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    //medico
    val ListaMedicos = medicosRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        getAllConsultas()
    }

    fun getAllConsultas() {
        consultasRepository.getAll()
            .onEach { tickets ->
                consultaList.value = tickets
            }
            .launchIn(viewModelScope)
    }

    fun saveConsulta(consulta: ConsultaEntity) {
        viewModelScope.launch {
            consultasRepository.save(consulta)
        }
    }

    suspend fun findConsulta(id: Int): ConsultaEntity? {
        return consultasRepository.find(id)
    }

    fun deleteConsulta(consulta: ConsultaEntity) {
        viewModelScope.launch {
            consultasRepository.delete(consulta)
        }
    }
    val consultasS: StateFlow<List<ConsultaEntity>> = consultasRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}