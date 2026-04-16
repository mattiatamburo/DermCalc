package DataBase

import androidx.room.*

@Entity(
    tableName = "OspedaleDottore",
    primaryKeys = ["idOspedale", "idDottore"],
    foreignKeys = [
        ForeignKey(
            entity = Ospedale::class,
            parentColumns = ["idOspedale"],
            childColumns = ["idOspedale"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Dottore::class,
            parentColumns = ["idDottore"],
            childColumns = ["idDottore"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OspedaleDottore(
    var idOspedale: Int,
    var idDottore: Int
)
