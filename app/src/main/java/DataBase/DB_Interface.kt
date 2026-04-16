package DataBase
import androidx.room.*;
@Dao
interface DB_Interface {
    //prendo tutte le diagnosi di un dottore
    @Query("SELECT * FROM Diagnosi WHERE idDottore = :idDottore")
    fun getAllDiagnosi(idDottore: Int): List<Diagnosi>;

    //controllo accesso login
    @Query("SELECT * FROM Accessi WHERE username = :username AND password = :password")
    fun checkLogin(username: String, password: String): Int;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccessi(accesso: Accessi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDottore(dottore: Dottore)


}