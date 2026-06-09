package edu.ucne.vitaslot.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.vitaslot.data.local.dao.MedicoDao
import edu.ucne.vitaslot.data.local.entities.MedicoEntity

@Database(
    entities = [
        MedicoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MedicoDb : RoomDatabase() {
    abstract fun medicoDao(): MedicoDao
}