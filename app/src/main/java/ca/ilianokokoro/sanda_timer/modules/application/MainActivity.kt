package ca.ilianokokoro.sanda_timer.modules.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ca.ilianokokoro.sanda_timer.modules.application.screens.main.MainScreen
import ca.ilianokokoro.sanda_timer.modules.application.theme.SandaTimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SandaTimerTheme {
                MainScreen()
            }
        }
    }
}
