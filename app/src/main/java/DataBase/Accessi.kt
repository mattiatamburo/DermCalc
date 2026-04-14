package DataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import java.security.KeyStore

@Entity(tableName = "Accessi",
        primaryKeys = ["idAccesso"],
        foreignKeys = [
            ForeignKey(
                entity = Dottore::class,
                parentColumns = ["idDottore"],
                childColumns = ["idDottore"]
            )
        ])
data class Accessi(
    var idAccesso: Int,
    var idDottore: Int,
    var username: String,
    var password: KeyStore.PasswordProtection
)