package happy.mjstudio.github.data.service

import happy.mjstudio.core.data.api.base.NetworkResponse
import happy.mjstudio.github.data.entity.GithubProfile
import happy.mjstudio.github.data.entity.GithubRepo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("user/followers")
    suspend fun listFollowers(@Query("per_page") perPage: Int = 100): NetworkResponse<List<GithubProfile>>

    @GET("users/{username}/following")
    suspend fun listFollowing(
        @Path("username") username: String = "mym0404", @Query("per_page") perPage: Int = 100
    ): List<GithubProfile>

    @GET("user/repos")
    suspend fun listRepos(
        @Query("per_page") perPage: Int = 100
    ): List<GithubRepo>
}