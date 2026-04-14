package DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Accessi::class,
        Dottore::class,
        Diagnosi::class,
        Ospedale::class,
        Paziente::class,
        CartellaClinica::class,
        OspedaleDottore::class
    ],
    version = 1
)
@TypeConverters(androidx.databinding.adapters.Converters::class) // Se hai campi Date, ti serve questo (vedi sotto)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbInterface(): DB_Interface
}