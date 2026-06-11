package edu.ucne.vitaslot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.vitaslot.data.local.database.MedicoDb
import edu.ucne.vitaslot.data.repository.ConsultasRepository
import edu.ucne.vitaslot.data.repository.MedicosRepository
import edu.ucne.vitaslot.data.repository.PacientesRepository
import edu.ucne.vitaslot.presentation.consulta.ConsultasViewModel
import edu.ucne.vitaslot.presentation.medico.MedicosViewModel
import edu.ucne.vitaslot.presentation.navigation.HomeNavHost
import edu.ucne.vitaslot.presentation.paciente.PacientesViewModel
import edu.ucne.vitaslot.ui.theme.VitaSlotTheme

class MainActivity : ComponentActivity() {
    private lateinit var medicoDb: MedicoDb

    private lateinit var medicosRepository: MedicosRepository
    private lateinit var medicoviewModel: MedicosViewModel
    private lateinit var pacientesRepository: PacientesRepository
    private lateinit var pacientesViewModel: PacientesViewModel
    private lateinit var consultasRepository: ConsultasRepository
    private lateinit var consultasViewModel: ConsultasViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        medicoDb = Room.databaseBuilder(
            applicationContext,
            MedicoDb::class.java,
            "Medico.db"
        ).fallbackToDestructiveMigration()
            .build()

        medicosRepository = MedicosRepository(medicoDb.MedicoDao())
        medicoviewModel = MedicosViewModel(medicosRepository)

        pacientesRepository = PacientesRepository(medicoDb.PacienteDao())
        pacientesViewModel = PacientesViewModel(pacientesRepository)

        consultasRepository = ConsultasRepository(medicoDb.ConsultaDao())
        consultasViewModel = ConsultasViewModel(
            consultasRepository,
            medicosRepository,
            pacientesRepository
        )



        setContent {
            VitaSlotTheme() {
                val nav = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        HomeNavHost(
                            navHostController = nav,
                            medicosViewModel = medicoviewModel,
                            pacientesViewModel = pacientesViewModel,
                            consultasViewModel = consultasViewModel
                        )
                    }
                }
            }
        }
    }
}