package DataBase

import androidx.room.*

@Dao
interface DB_Interface {
    @Query("SELECT * FROM Diagnosi WHERE idDottore = :idDottore")
    fun getAllDiagnosi(idDottore: Int): List<Diagnosi>

    @Query("SELECT * FROM Dottore JOIN Accessi USING (idDottore) WHERE username = :username AND password = :password")
    fun checkLogin(username: String, password: String): Dottore?

    @Query("SELECT * FROM Dottore")
    fun leggiDottore(): List<Dottore>

    @Query("SELECT * FROM Dottore WHERE idDottore = :id")
    fun getDottoreById(id: Int): Dottore?

    @Query("SELECT * FROM Paziente")
    fun getPazienti(): List<Paziente>

    @Query("SELECT * FROM Paziente WHERE nome LIKE '%' || :input || '%' OR cognome LIKE '%' || :input || '%'")
    fun searchPaziente(input: String): List<Paziente>

    @Query("DELETE FROM Paziente WHERE idPaziente > 3")
    fun removeDavide()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccessi(accesso: Accessi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDottore(dottore: Dottore)

    @Query("UPDATE Dottore SET nome = :nome, cognome = :cognome, cellulare = :cellulare, codFiscale = :codFiscale, email = :email, dataNascita = :dataNascita WHERE idDottore = :id")
    fun modifyDottore(nome: String, cognome: String, cellulare: String, codFiscale: String, email: String, dataNascita: java.util.Date, id: Int): Int

    @Query("SELECT * FROM Paziente WHERE idPaziente = :id")
    fun getPazienteById(id: Int): Paziente?

    @Query("DELETE FROM Paziente")
    fun removePazienti()

    @Query("DELETE FROM Diagnosi")
    fun removeDiagnosi()

    @Query("DELETE FROM CartellaClinica")
    fun removeCartelleCliniche()

    @Query("DELETE FROM sqlite_sequence WHERE name='Paziente'")
    fun resetPazienteIndex()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaziente(paziente: Paziente): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartellaClinica(cartellaClinica: CartellaClinica)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiagnosi(diagnosi: Diagnosi)

    @Query("SELECT * FROM Diagnosi WHERE idDiagnosi = :idDiagnosi")
    fun getDiagnosiById(idDiagnosi: Int): Diagnosi?
    @Query("SELECT D.* FROM Diagnosi D JOIN CartellaClinica C ON D.idCartellaClinica = C.idCartellaClinica WHERE C.idPaziente = :idPaziente")
    fun getDiagnosiByPaziente(idPaziente: Int): List<Diagnosi>

    @Query("SELECT idCartellaClinica FROM CartellaClinica WHERE idPaziente = :idPaziente")
    fun getCartellaClinica(idPaziente: Int): Int
}
