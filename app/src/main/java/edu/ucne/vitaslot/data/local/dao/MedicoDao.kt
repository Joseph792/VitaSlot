package edu.ucne.vitaslot.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.vitaslot.data.local.entities.MedicoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicoDao {
    @Upsert()
    suspend fun save(medico: MedicoEntity)
    @Query(
        """
        SELECT * 
        FROM Medicos 
        WHERE medicoId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): MedicoEntity?
    @Delete
    suspend fun delete(ocupacion: MedicoEntity)
    @Query("SELECT * FROM Medicos")
    fun getAll(): Flow<List<MedicoEntity>>
}