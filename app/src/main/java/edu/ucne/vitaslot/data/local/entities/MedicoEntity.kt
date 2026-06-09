package edu.ucne.vitaslot.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Medicos")
data class MedicoEntity(
    @PrimaryKey
    val medicoId: Int? = null,
    val nombre: String = "",
    val apellido: String = "",
    val direccion: String = "",
    val telefono: Int = 0,
    val correo: String = "",
    val fechaNacimiento: String = "",
    val especialidad: String = "",
)