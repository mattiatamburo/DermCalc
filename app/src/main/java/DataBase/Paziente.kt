package DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Paziente")
data class Paziente(
    @PrimaryKey(autoGenerate = true)
    var idPaziente: Int = 0,
    var nome: String,
    var cognome: String,
    var codFiscale: String,
    var email: String,
    var cellulare: String,
    var dataNascita: Date
)