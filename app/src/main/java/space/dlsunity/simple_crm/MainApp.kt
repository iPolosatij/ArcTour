package space.dlsunity.simple_crm

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication
import com.parse.Parse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import space.dlsunity.simple_crm.di.dataModule
import space.dlsunity.simple_crm.di.domainModule
import space.dlsunity.simple_crm.di.networkModule
import space.dlsunity.simple_crm.di.presentationModule

class MainApp : MultiDexApplication(), LifecycleObserver {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    var sApplication = this

    fun getContext(): Application {
        return sApplication
    }

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        sApplication = this
        setUpLightMode()
        setUpVectorCompat()
        setUpKoin()
        setUpLogger()
        setUpNotificationChannel()
        runChatService(applicationContext)
    }

    private fun setUpLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setUpVectorCompat() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(sApplication)
            modules(
                listOf(
                    networkModule(
                        serverUrl = space.dlsunity.simple_crm.Config.BASE_URL,
                        uploadTimeOut = space.dlsunity.simple_crm.Config.OKHTTP_TIMEOUT_60S,
                    ),
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }

    private fun runChatService(context: Context) {
        //val intent = Intent(context, ChatService::class.java)
       // context.startService(intent)
    }

    private fun setUpLogger() {
    }

    private fun setUpNotificationChannel() {
    }
}
