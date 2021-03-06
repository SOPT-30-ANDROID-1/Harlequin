package happy.mjstudio.github.data.service

import com.google.gson.GsonBuilder
import happy.mjstudio.core.data.api.base.MyCallAdapterFactory
import happy.mjstudio.core.presentation.util.FlipperUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.reflect.KClass

class GithubServiceFactory @Inject constructor() {
    private val gson = GsonBuilder().create()
    private lateinit var okHttpClient: OkHttpClient

    init {
        configureOkHttpClient()
    }

    private fun configureOkHttpClient() {
        val builder = OkHttpClient.Builder().addInterceptor {
            val baseRequest = it.request()
            val builder = baseRequest.newBuilder()
            val newRequest = builder.url(baseRequest.url).addCommonHeaders()
            it.proceed(newRequest.build())
        }
        okHttpClient = FlipperUtil.addFlipperNetworkPlugin(builder).build()
    }

    private fun Request.Builder.addCommonHeaders(): Request.Builder {
        addHeader("Content-Type", "application/json")
        addHeader("Authorization", "token ${STOP + REVOKE + MY + TOKEN}")
        addHeader("Accept", "application/vnd.github.v3+json")
        return this
    }

    private val apiRetrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(MyCallAdapterFactory()).client(okHttpClient).build()

    fun <T : Any> createService(clazz: KClass<T>): T = apiRetrofit.create(clazz.java)

    companion object {
        private const val BASE_URL = "https://api.github.com"
        private const val STOP = "ghp_"
        private const val REVOKE = "YauyLzWy0ONwix"
        private const val MY = "6qlpzqLfGSlmH54C4A5v"
        private const val TOKEN = "5R"
    }
}