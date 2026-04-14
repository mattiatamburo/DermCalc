package DataBase

import androidx.room.Entity
import androidx.room.ForeignKey

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
    var idDiagnosi: Int,
    var idDottore: Int,
    var idCartellaClinica: Int,
    var note: String
)