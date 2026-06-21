package DataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import java.sql.Date

@Entity(
    tableName = "Diagnosi",
    primaryKeys = ["idDiagnosi"],
    foreignKeys = [
        ForeignKey(
            entity = Dottore::class,
            parentColumns = ["idDottore"],
            childColumns = ["idDottore"]
        ),
        ForeignKey(
            entity = CartellaClinica::class,
            parentColumns = ["idCartellaClinica"],
            childColumns = ["idCartellaClinica"]
        )
    ]
)
data class Diagnosi(
    var idDiagnosi: Int = 0,
    var idDottore: Int,
    var dataDiagnosi: Date,
    var tipoCalcolatore: String,
    val punteggioTotale: Double,
    val severita: String,
    var idCartellaClinica: Int,
    var note: String
)