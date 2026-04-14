package DataBase
import androidx.room.*;

@Entity(
    tableName = "CartellaClinica",
    primaryKeys = ["idCartellaClinica"],
    foreignKeys = [
        ForeignKey(
            entity = Paziente::class, // La classe dell'entità a cui ti riferisci
            parentColumns = ["idPaziente"], // La colonna chiave primaria nel Paziente
            childColumns = ["idPaziente"], // La colonna chiave esterna in CartellaClinica
            onDelete = ForeignKey.CASCADE // Comportamento se il paziente viene cancellato
        )
    ]
)
data class CartellaClinica(
    var idCartellaClinica: Int,
    var idPaziente: Int

)