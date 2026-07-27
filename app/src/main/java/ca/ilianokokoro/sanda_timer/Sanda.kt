package ca.ilianokokoro.sanda_timer

import android.app.Application
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
import ca.ilianokokoro.sanda_timer.core.receivers.BootReceiver
import ca.ilianokokoro.sanda_timer.core.repositories.TimerRepository

class Sanda : Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationManager.init(this)

        BootReceiver.onBoot = {
            TimerRepository(this).clearTimers()
        }
    }

}