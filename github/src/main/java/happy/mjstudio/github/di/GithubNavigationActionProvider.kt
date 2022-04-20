package happy.mjstudio.github.di

import androidx.navigation.NavDirections
import happy.mjstudio.github.data.entity.GithubProfile

interface GithubNavigationActionProvider {
    fun detailAction(profile: GithubProfile): NavDirections
}