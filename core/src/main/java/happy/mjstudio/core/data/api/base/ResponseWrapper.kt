package happy.mjstudio.core.data.api.base

data class ResponseWrapper<T : Any>(
    val status: Int,
    val message: String,
    val data: T,
)