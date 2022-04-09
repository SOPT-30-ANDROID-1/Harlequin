package happy.mjstudio.harlequin.di

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import happy.mjstudio.harlequin.auth.provider.AuthProviderImpl
import happy.mjstudio.harlequin.util.localstorage.LocalStorage
import happy.mjstudio.harlequin.util.localstorage.SharedPreferencesLocalStorage
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcherAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    @Named("application")
    fun provideApplicationCoroutineScope() = CoroutineScope(SupervisorJob())

    @Provides
    fun provideDisplayMetrics(app: Application): DisplayMetrics = app.resources.displayMetrics

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {
    @Binds
    @Singleton
    abstract fun bindLocalStorage(storage: SharedPreferencesLocalStorage): LocalStorage

    @Binds
    @Singleton
    abstract fun bindThemeSwitcher(switcher: ThemeSwitcherAndroid): ThemeSwitcher

    @Binds
    @Singleton
    abstract fun bindAuthProvider(provider: AuthProviderImpl): AuthProvider
}

