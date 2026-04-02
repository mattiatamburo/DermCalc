package DataBase

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "Dottore")
data class Dottore(
    @PrimaryKey(true)
    var idDottore: Int,
    var nome: String,
    var cognome: String
)
