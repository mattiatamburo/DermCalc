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
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbInterface(): DB_Interface
}