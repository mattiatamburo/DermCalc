package DataBase
import androidx.room.*;

@Entity(
    tableName = "CartellaClinica",
    primaryKeys = ["idCartellaClinica"],
    foreignKeys = [
        ForeignKey(
            entity = Paziente::class,
            parentColumns = ["idPaziente"],
            childColumns = ["idPaziente"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartellaClinica(
    var idCartellaClinica: Int = 0,
    var idPaziente: Int
)