package DataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Diagnosi",
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
    @PrimaryKey(autoGenerate = true)
    var idDiagnosi: Int = 0,
    var idDottore: Int,
    var dataDiagnosi: Date,
    var tipoCalcolatore: String,
    val punteggioTotale: Double,
    val severita: Int,
    var idCartellaClinica: Int,
    var note: String
)