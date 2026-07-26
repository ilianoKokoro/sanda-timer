package ca.ilianokokoro.sanda_timer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ca.ilianokokoro.sanda_timer.core.helpers.AppIntent
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper
import ca.ilianokokoro.sanda_timer.ui.navigation.NavigationRoot
import ca.ilianokokoro.sanda_timer.ui.theme.SandaTimerTheme
import kotlinx.coroutines.flow.MutableSharedFlow

class MainActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    private val appIntentFlow = MutableSharedFlow<AppIntent>(
        replay = 1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SandaTimerTheme {
                NavigationRoot(
                    appIntentFlow
                )
            }
        }

        requestNotificationPermission()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        IntentHelper.parse(intent)?.let {
            appIntentFlow.tryEmit(it)
        }
    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
