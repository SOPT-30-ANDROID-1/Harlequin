package happy.mjstudio.github.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import happy.mjstudio.github.data.service.GithubService
import happy.mjstudio.github.data.service.GithubServiceFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {
    @Provides
    @Singleton
    fun providesGithubServiceFactory() = GithubServiceFactory()

    @Provides
    @Singleton
    fun providesGithubService(factory: GithubServiceFactory) = factory.createService(GithubService::class)
}