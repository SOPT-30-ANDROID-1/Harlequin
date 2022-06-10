package happy.mjstudio.core.data.api.base

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class NetworkResponseAdapter<T : Any>(
    private val bodyType: Type,
) : CallAdapter<T, Call<NetworkResponse<T>>> {

    override fun responseType(): Type = bodyType

    override fun adapt(call: Call<T>): Call<NetworkResponse<T>> {
        return MyCall(call, bodyType)
    }
}