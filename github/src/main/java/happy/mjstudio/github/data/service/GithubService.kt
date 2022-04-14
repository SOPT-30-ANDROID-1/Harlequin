package happy.mjstudio.github.data.service

import happy.mjstudio.github.data.dto.GithubFollowerDTO
import retrofit2.http.GET

interface GithubService {
    @GET("user/followers")
    suspend fun listFollowers(): List<GithubFollowerDTO>
}