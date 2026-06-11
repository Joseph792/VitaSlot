package edu.ucne.vitaslot.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Consultas",
    foreignKeys = [
        ForeignKey(
            entity = MedicoEntity::class,
            parentColumns = ["medicoId"],
            childColumns = ["medicoId"]
        ),
        ForeignKey(
            entity = PacienteEntity::class,
            parentColumns = ["pacienteId"],
            childColumns = ["pacienteId"]
        )
    ]
)
data class ConsultaEntity(
    @PrimaryKey(autoGenerate = true)
    val consultaId: Int? = null,
    val fecha: Date = Date(),
    val hora: Date = Date(),
    val medicoId: Int,
    val pacienteId: Int
)