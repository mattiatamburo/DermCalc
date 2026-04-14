package DataBase

import androidx.room.PrimaryKey

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
    @PrimaryKey(false)
    var idOspedale: Int,
    var idDottore: Int
)
