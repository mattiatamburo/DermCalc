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

    fun checkLogin(user: String, pass: String): Int {
        val dottore = dbInterface.checkLogin(user, pass)
        //println(dottore?.idDottore ?: "dottore non trovato");
        println("idDottore: " + dottore?.idDottore ?: "dottore non trovato");

        return dottore?.idDottore ?: -1;
    }

    fun insertAccessi(accesso: Accessi) {
        dbInterface.insertAccessi(accesso)
    }

    fun insertDottore(dottore: Dottore){
        dbInterface.insertDottore(dottore)
    }

    fun leggiDottore(): List<Dottore> {
        return dbInterface.leggiDottore()
    }

}