package happy.mjstudio.core.data.api.base

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

internal fun <T> Response<T>.asNetworkResponse(
    bodyType: Type
): NetworkResponse<T> {
    return if (isSuccessful) {
        parseSuccessfulResponse(this, bodyType)
    } else {
        parseUnsuccessfulResponse()
    }
}

private fun <T> parseSuccessfulResponse(response: Response<T>, successType: Type): NetworkResponse<T> {
    val responseBody: T? = response.body()
    if (responseBody == null) {
        if (successType === Unit::class.java) {
            @Suppress("UNCHECKED_CAST") return NetworkResponse.Success(Unit) as NetworkResponse<T>
        }
        return NetworkResponse.ServerError()
    }
    return NetworkResponse.Success(responseBody)
}

private fun <T> parseUnsuccessfulResponse(): NetworkResponse.Error<T> {
    return NetworkResponse.ServerError()
}

internal fun <T> Throwable.asNetworkResponse(): NetworkResponse<T> {
    return when (this) {
        is IOException -> NetworkResponse.NetworkError()
        is HttpException -> NetworkResponse.ServerError()
        is JsonSyntaxException -> NetworkResponse.JsonParsingError()
        else -> NetworkResponse.UnknownError()
    }
}