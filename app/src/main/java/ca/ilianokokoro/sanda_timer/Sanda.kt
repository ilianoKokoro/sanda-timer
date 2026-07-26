package ca.ilianokokoro.sanda_timer

import android.app.Application
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager

class Sanda : Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationManager.init(this)
    }

}