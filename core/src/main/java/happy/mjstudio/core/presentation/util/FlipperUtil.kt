package happy.mjstudio.core.presentation.util

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import happy.mjstudio.core.BuildConfig
import okhttp3.OkHttpClient

object FlipperUtil {
    private val flipperNetworkPlugin = NetworkFlipperPlugin()

    fun init(app: Application) {
        SoLoader.init(app, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(app)) {
            val client = AndroidFlipperClient.getInstance(app)
            client.addPlugin(InspectorFlipperPlugin(app, DescriptorMapping.withDefaults()))
            client.addPlugin(flipperNetworkPlugin)
            client.addPlugin(SharedPreferencesFlipperPlugin(app, "hey"))
            client.start()
        }
    }

    fun addFlipperNetworkPlugin(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder.addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
    }
}