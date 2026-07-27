package ca.ilianokokoro.sanda_timer.modules.application

import android.app.Application
import ca.ilianokokoro.sanda_timer.core.data.repositories.TimerRepository
import ca.ilianokokoro.sanda_timer.core.managers.NotificationManager
import ca.ilianokokoro.sanda_timer.core.receivers.BootReceiver

class Sanda : Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationManager.init(this)

        BootReceiver.onBoot = {
            TimerRepository(this).clearTimers()
        }
    }

}