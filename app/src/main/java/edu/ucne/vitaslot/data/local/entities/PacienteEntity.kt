package edu.ucne.vitaslot.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pacientes")
data class PacienteEntity(
    @PrimaryKey
    val pacienteId: Int? = null,
    val nombre: String = "",
    val apellido: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val correo: String = ""
)