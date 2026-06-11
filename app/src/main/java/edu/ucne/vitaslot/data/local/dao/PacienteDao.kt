package edu.ucne.vitaslot.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.vitaslot.data.local.entities.PacienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PacienteDao {
    @Upsert()
    suspend fun save(paciente: PacienteEntity)
    @Query(
        """
        SELECT * 
        FROM Pacientes
        WHERE pacienteId=:id
        LIMIT 1
        """
    )
    suspend fun find(id: Int): PacienteEntity?
    @Delete
    suspend fun delete(paciente: PacienteEntity)
    @Query("SELECT * FROM Pacientes")
    fun getAll(): Flow<List<PacienteEntity>>
}