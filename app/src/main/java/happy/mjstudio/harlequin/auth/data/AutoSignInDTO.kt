package happy.mjstudio.harlequin.auth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autoSignIn")
data class AutoSignInDTO(
    @PrimaryKey val id: String,
    val password: String,
)