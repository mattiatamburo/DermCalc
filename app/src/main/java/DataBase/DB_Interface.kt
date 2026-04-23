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
    fun removeDavide();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccessi(accesso: Accessi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDottore(dottore: Dottore)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaziente(paziente: Paziente)
}
