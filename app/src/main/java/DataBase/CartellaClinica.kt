package DataBase
import androidx.room.Entity

@Entity(tableName = "CartellaClinica")
data class CartellaClinica(
    var idCartellaClinica: Int,
    var idPaziente: Int

)