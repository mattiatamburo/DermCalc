package DataBase

import android.content.Context
import androidx.room.Room

class DB_Manager(context: Context) {

    // Crea un'unica istanza del database che contiene tutte le tabelle
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "dermcalc_database"
    )
        .allowMainThreadQueries() // Nota: In un'app reale usa i thread!
        .build()

    private val dbInterface = db.dbInterface()

    // Metodi per usare le query
    fun getAllDiagnosi(idDottore: Int) = dbInterface.getAllDiagnosi(idDottore)

    fun checkLogin(user: String, pass: String): Boolean {
        println(dbInterface.checkLogin(user, pass))
        return dbInterface.checkLogin(user, pass) != null
    }
}