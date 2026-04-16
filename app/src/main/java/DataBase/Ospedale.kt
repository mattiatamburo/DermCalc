package DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ospedale")
data class Ospedale(
    @PrimaryKey(autoGenerate = true)
    var idOspedale: Int = 0,
    var indirizzo: String
)