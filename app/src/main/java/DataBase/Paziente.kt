package DataBase

import android.text.SpannableStringBuilder
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Paziente")
data class Paziente(
    @PrimaryKey(true)
    var idPaziente: Int,
    var nome: String,
    var cognome: String,
    var codFiscale: String
)