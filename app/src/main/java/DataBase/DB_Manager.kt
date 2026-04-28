package DataBase

import android.content.Context
import androidx.room.Room
import java.util.Date

class DB_Manager(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "dermcalc_database"
    )
        .allowMainThreadQueries()
        .build()

    private val dbInterface = db.dbInterface()

    fun getAllDiagnosi(idDottore: Int) = dbInterface.getAllDiagnosi(idDottore)

    fun checkLogin(user: String, pass: String) = dbInterface.checkLogin(user, pass)

    fun leggiDottore() = dbInterface.leggiDottore()

    fun getDottoreById(id: Int) = dbInterface.getDottoreById(id)

    fun getPazienti() = dbInterface.getPazienti()

    fun removeDavide() = dbInterface.removeDavide()

    fun searchPaziente(input: String) = dbInterface.searchPaziente(input)

    fun insertAccessi(accesso: Accessi) = dbInterface.insertAccessi(accesso)

    fun insertDottore(dottore: Dottore) = dbInterface.insertDottore(dottore)

    fun insertPaziente(paziente: Paziente) = dbInterface.insertPaziente(paziente)

    fun modifyDottore(nome: String, cognome: String, cellulare: String, codFiscale: String, email: String, dataNascita: Date, id: Int): Boolean {
        return dbInterface.modifyDottore(nome, cognome, cellulare, codFiscale, email, dataNascita, id) > 0
    }
}
