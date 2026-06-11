package edu.ucne.vitaslot.data.repository

import edu.ucne.vitaslot.data.local.dao.PacienteDao
import edu.ucne.vitaslot.data.local.entities.PacienteEntity
import kotlinx.coroutines.flow.Flow

class PacientesRepository(
    private val dao: PacienteDao
) {
    suspend fun save(paciente: PacienteEntity) = dao.save(paciente)

    suspend fun find(id: Int): PacienteEntity? = dao.find(id)

    suspend fun delete(paciente: PacienteEntity) = dao.delete(paciente)

    fun getAll(): Flow<List<PacienteEntity>> = dao.getAll()
}