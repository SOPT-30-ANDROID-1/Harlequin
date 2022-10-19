package happy.mjstudio.harlequin.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import happy.mjstudio.harlequin.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesAppDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase").build()
}