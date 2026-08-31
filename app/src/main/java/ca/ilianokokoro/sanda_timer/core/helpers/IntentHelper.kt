package ca.ilianokokoro.sanda_timer.core.helpers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ca.ilianokokoro.sanda_timer.MainActivity
import ca.ilianokokoro.sanda_timer.core.Constants
import ca.ilianokokoro.sanda_timer.modules.application.DoneActivity

sealed interface AppIntent {
    data object OpenTimerPage : AppIntent
    data object OpenApp : AppIntent
}

object IntentHelper {
    private const val APP_ID = "ca.ilianokokoro.sanda_timer" // TODO extract
    private const val PREFIX = "${APP_ID}.action."
    const val ACTION_OPEN_TIMER_PAGE =
        "${PREFIX}OPEN_TIMER_PAGE"

    const val ACTION_OPEN_APP =
        "${PREFIX}OPEN_APP"

    const val EXTRA_APP_INTENT_ACTION = "app_intent_action"

    fun openTimerPage(
        context: Context,
    ) = Intent(context, MainActivity::class.java).apply {
        action = ACTION_OPEN_TIMER_PAGE
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    fun openApp(
        context: Context,
    ) = Intent(context, MainActivity::class.java).apply {
        action = ACTION_OPEN_APP
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    fun openDone(
        context: Context,
        timerId: Long,
        durationSeconds: Long,
    ) = Intent(context, DoneActivity::class.java).apply {
        putExtra(Constants.TimerReceiver.TIMER_ID, timerId)
        putExtra(Constants.TimerReceiver.DURATION_SECONDS, durationSeconds)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    fun openDonePendingIntent(
        context: Context,
        timerId: Long,
        durationSeconds: Long,
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            timerId.hashCode() + 1,
            openDone(context, timerId, durationSeconds),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

    fun openAppPendingIntent(
        context: Context,
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            ACTION_OPEN_APP.hashCode(),
            openApp(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

    fun openTimerPagePendingIntent(
        context: Context,
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            ACTION_OPEN_TIMER_PAGE.hashCode(),
            openTimerPage(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )


    fun parse(intent: Intent): AppIntent? =
        when (intent.action ?: intent.getStringExtra(EXTRA_APP_INTENT_ACTION)) {

            ACTION_OPEN_TIMER_PAGE -> AppIntent.OpenTimerPage
            ACTION_OPEN_APP -> AppIntent.OpenApp

            else -> null
        }
}