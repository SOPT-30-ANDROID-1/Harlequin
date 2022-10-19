package happy.mjstudio.harlequin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import happy.mjstudio.harlequin.auth.data.AutoSignInDTO
import happy.mjstudio.harlequin.auth.data.AutoSignInDao

@Database(entities = [AutoSignInDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun autoSignInDao(): AutoSignInDao
}