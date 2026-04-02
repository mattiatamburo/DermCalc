package DataBase

import androidx.room.Entity

@Entity(tableName = "Accessi")
data class Accessi(
    var idAccesso: Int,
    var idDottore: Int
)