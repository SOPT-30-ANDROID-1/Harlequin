package happy.mjstudio.harlequin.di.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import happy.mjstudio.github.di.GithubNavigationActionProvider

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @ActivityScoped
    @Binds
    abstract fun bindsGithubNavigationActionProvider(provider: GithubNavigationActionProviderImpl): GithubNavigationActionProvider
}