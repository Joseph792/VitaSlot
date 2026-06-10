package edu.ucne.vitaslot.data.repository

import edu.ucne.vitaslot.data.local.dao.MedicoDao
import edu.ucne.vitaslot.data.local.entities.MedicoEntity
import kotlinx.coroutines.flow.Flow

class MedicosRepository(
    private val dao: MedicoDao
) {
    suspend fun save(medico: MedicoEntity) = dao.save(medico)

    suspend fun find(id: Int): MedicoEntity? = dao.find(id)

    suspend fun delete(medico: MedicoEntity) = dao.delete(medico)

    fun getAll(): Flow<List<MedicoEntity>> = dao.getAll()
}