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
        .fallbackToDestructiveMigration()
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

    fun insertPaziente(paziente: Paziente): Long {return dbInterface.insertPaziente(paziente)}

    fun insertCartellaClinica(cartella: CartellaClinica) = dbInterface.insertCartellaClinica(cartella)

    fun modifyDottore(nome: String, cognome: String, cellulare: String, codFiscale: String, email: String, dataNascita: Date, id: Int): Boolean {
        return dbInterface.modifyDottore(nome, cognome, cellulare, codFiscale, email, dataNascita, id) > 0
    }

    fun getPazienteById(id: Int) = dbInterface.getPazienteById(id)

    fun removePazienti() = dbInterface.removePazienti();

    fun removeDiagnosi() = dbInterface.removeDiagnosi()

    fun removeCartelleCliniche() = dbInterface.removeCartelleCliniche()
    fun resetPazienteIndex() = dbInterface.resetPazienteIndex();

    fun getDiagnosiById(idDiagnosi: Int): Diagnosi? {return dbInterface.getDiagnosiById(idDiagnosi)}
    fun insertDiagnosi(diagnosi: Diagnosi) = dbInterface.insertDiagnosi(diagnosi);

    fun getDiagnosiByPaziente(idPaziente: Int): List<Diagnosi> {return dbInterface.getDiagnosiByPaziente(idPaziente)}

    fun getCartellaClinica(idPaziente: Int): Int {return dbInterface.getCartellaClinica(idPaziente)}
    
    fun checkPazienti() = dbInterface.checkPazienti()

    fun checkPrimoAccesso() = dbInterface.checkPrimoAccesso()

    fun getAccessoByDottoreId(idDottore: Int) = dbInterface.getAccessoByDottoreId(idDottore)

    fun updatePassword(idAccesso: Int, newPassword: String) = dbInterface.updatePassword(idAccesso, newPassword)
}
