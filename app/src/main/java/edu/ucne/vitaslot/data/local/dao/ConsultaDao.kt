package edu.ucne.vitaslot.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsultaDao {
    @Upsert()
    suspend fun save(consulta: ConsultaEntity)
    @Query(
        """
        SELECT * 
        FROM Consultas
        WHERE consultaId=:id
        LIMIT 1
        """
    )
    suspend fun find(id: Int): ConsultaEntity?
    @Delete()
    suspend fun delete(consulta: ConsultaEntity)
    @Query("SELECT * FROM Consultas")
    fun getAll(): Flow<List<ConsultaEntity>>
}