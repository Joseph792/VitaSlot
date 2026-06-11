package edu.ucne.vitaslot.data.repository

import edu.ucne.vitaslot.data.local.dao.ConsultaDao
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import kotlinx.coroutines.flow.Flow

class ConsultasRepository(
    private val dao: ConsultaDao
) {
    suspend fun save(consulta: ConsultaEntity) = dao.save(consulta)

    suspend fun find(id: Int) = dao.find(id)

    suspend fun delete(consulta: ConsultaEntity) = dao.delete(consulta)

    fun getAll(): Flow<List<ConsultaEntity>> = dao.getAll()
}