package DataBase

import androidx.room.Entity

@Entity(tableName = "Ospedale")
data class Ospedale(
    var idOspedale: Int
)
