package happy.mjstudio.harlequin.di.navigation

import happy.mjstudio.github.data.entity.GithubProfile
import happy.mjstudio.github.di.GithubNavigationActionProvider
import happy.mjstudio.harlequin.presentation.master.base.MasterFragmentDirections
import javax.inject.Inject

class GithubNavigationActionProviderImpl @Inject constructor() : GithubNavigationActionProvider {
    override fun detailAction(profile: GithubProfile) =
        MasterFragmentDirections.actionMasterFragmentToGithubDetailFragment(profile)
}