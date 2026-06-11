package edu.ucne.vitaslot.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import edu.ucne.vitaslot.data.local.dao.ConsultaDao
import edu.ucne.vitaslot.data.local.dao.MedicoDao
import edu.ucne.vitaslot.data.local.dao.PacienteDao
import edu.ucne.vitaslot.data.local.entities.MedicoEntity
import edu.ucne.vitaslot.data.local.entities.PacienteEntity
import edu.ucne.vitaslot.data.local.entities.ConsultaEntity
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Database(
    entities = [
        MedicoEntity::class,
        PacienteEntity::class,
        ConsultaEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MedicoDb : RoomDatabase() {
    abstract fun MedicoDao(): MedicoDao
    abstract fun PacienteDao(): PacienteDao
    abstract fun ConsultaDao(): ConsultaDao
}