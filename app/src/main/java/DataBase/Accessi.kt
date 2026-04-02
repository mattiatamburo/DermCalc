package DataBase

import androidx.room.Entity
import java.security.KeyStore

@Entity(tableName = "Accessi")
data class Accessi(
    var idAccesso: Int,
    var idDottore: Int,
    var username: String,
    var password: KeyStore.PasswordProtection
)