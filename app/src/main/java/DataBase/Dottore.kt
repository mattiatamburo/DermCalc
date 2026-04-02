package DataBase

import androidx.room.PrimaryKey
import androidx.room.Entity
import java.util.Date

@Entity(tableName = "Dottore")
data class Dottore(
    @PrimaryKey(true)
    var idDottore: Int,
    var nome: String,
    var cognome: String,
    var cellulare: String,
    var codFiscale: String,
    var email: String,
    var dataNascita: Date
)