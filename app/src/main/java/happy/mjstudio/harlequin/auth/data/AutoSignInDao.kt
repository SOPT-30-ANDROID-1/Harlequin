package happy.mjstudio.harlequin.auth.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AutoSignInDao {
    @Query("SELECT * FROM autoSignIn")
    suspend fun getAll(): List<AutoSignInDTO>

    @Insert
    suspend fun insert(dto: AutoSignInDTO)

    @Query("DELETE FROM autoSignIn")
    suspend fun deleteAll()
}