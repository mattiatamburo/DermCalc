package DataBase
import androidx.room.*;

@Entity(
    tableName = "CartellaClinica",
    foreignKeys = [
        ForeignKey(
            entity = Paziente::class,
            parentColumns = ["idPaziente"],
            childColumns = ["idPaziente"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idPaziente"])]
)
data class CartellaClinica(
    @PrimaryKey(autoGenerate = true)
    var idCartellaClinica: Int = 0,
    var idPaziente: Int
)