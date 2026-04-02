package DataBase

import androidx.room.PrimaryKey

import androidx.room.Entity

@Entity(
    tableName = "OspedaleDottore",
    primaryKeys = ["idOspedale", "idDottore"]
    )
data class OspedaleDottore(
    @PrimaryKey(false)
    var idOspedale: Int,
    var idDottore: Int

)
