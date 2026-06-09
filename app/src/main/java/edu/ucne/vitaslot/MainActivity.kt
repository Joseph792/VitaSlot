package edu.ucne.vitaslot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import edu.ucne.vitaslot.ui.theme.VitaSlotTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var medicoDb: MedicoDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        medicoDb = Room.databaseBuilder(
            applicationContext,
            MedicoDb::class.java,
            "Medico.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            VitaSlotTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MedicoScreen()
                    }
                }
            }
        }
    }

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

    @Composable
    fun MedicoScreen(
    ) {
        var nombre: String by remember { mutableStateOf("") }
        var apellido: String by remember { mutableStateOf("") }
        var direccion: String by remember { mutableStateOf("") }
        var telefono: Int by remember { mutableStateOf(0) }
        var correo: String by remember { mutableStateOf("") }
        var fechaNacimiento: String by remember { mutableStateOf("") }
        var especialidad: String by remember { mutableStateOf("") }

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Registro de Medicos")
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        OutlinedTextField(
                            label = { Text(text = "Nombre") },
                            value = nombre,
                            onValueChange = { nombre = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Apellido") },
                            value = apellido,
                            onValueChange = { apellido = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Direccion") },
                            value = direccion,
                            onValueChange = { direccion = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Telefono") },
                            value = telefono.toString(),
                            onValueChange = { telefono = it.toIntOrNull() ?: 0 },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Correo") },
                            value = correo,
                            onValueChange = { correo = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Fecha de Nacimiento") },
                            value = fechaNacimiento,
                            onValueChange = { fechaNacimiento = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Especialidad") },
                            value = especialidad,
                            onValueChange = { especialidad = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.padding(2.dp))
                        /*errorMessage?.let {
                            Text(text = it, color = Color.Red)
                        }*/
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "new button"
                                )
                                Text(text = "Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    /*if (nombre.isBlank())
                                        errorMessage = "Nombre vacio"*/

                                    /*if (apellido.isBlank())
                                        errorMessage = "Apellido Vacio"

                                    if (direccion.isBlank())
                                        errorMessage = "Direccion Vacia"

                                    if (telefono.isBlank())
                                        errorMessage = "Telefono Vacio"

                                    if (correo.isBlank())
                                        errorMessage = "Correo Vacio"

                                    if (fechaNacimiento.isBlank())
                                        errorMessage = "Fecha de Nacimiento Vacia"

                                    if (especialidad.isBlank())
                                        errorMessage = "Especialidad Vacia"*/


                                    scope.launch {
                                        saveMedico(
                                            MedicoEntity(
                                                nombre = nombre,
                                                apellido = apellido,
                                                direccion = direccion,
                                                telefono = telefono,
                                                correo = correo,
                                                fechaNacimiento = fechaNacimiento,
                                                especialidad = especialidad
                                            )
                                        )
                                        nombre = ""
                                        apellido = ""
                                        direccion = ""
                                        telefono = 0
                                        correo = ""
                                        fechaNacimiento = ""
                                        especialidad = ""
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "save button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }
                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                val medicoList by medicoDb.medicoDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                MedicoListScreen(medicoList)
            }
        }
    }

    @Composable
    fun MedicoListScreen(medicoList: List<MedicoEntity>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de Medicos")
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(medicoList) {
                    MedicoRow(it)
                }
            }
        }
    }

    @Composable
    private fun MedicoRow(it: MedicoEntity) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = it.medicoId.toString())
            Text(
                modifier = Modifier.weight(2f),
                text = it.nombre,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.apellido,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.direccion,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(modifier = Modifier.weight(1f),
                text = it.telefono.toString(),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.correo,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.fechaNacimiento,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.especialidad,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        HorizontalDivider()
    }

    private suspend fun saveMedico(medico: MedicoEntity) {
        medicoDb.medicoDao().save(medico)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VitaSlotTheme {
        Greeting("Android")
    }
}