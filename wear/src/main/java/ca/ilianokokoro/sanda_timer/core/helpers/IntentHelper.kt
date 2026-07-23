package ca.ilianokokoro.sanda_timer.core.helpers

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ModifiersBuilders
import ca.ilianokokoro.sanda_timer.modules.application.MainActivity

sealed interface AppIntent {
    data class OpenTimer(val timerId: Long) : AppIntent
    data object OpenNewScreen : AppIntent
    data object OpenApp : AppIntent
}

object IntentHelper {
    private const val APP_ID = "ca.ilianokokoro.sanda_timer" // TODO extract
    private val MAIN_ACTIVITY_COMPONENT_NAME = ComponentName(
        APP_ID,
        "$APP_ID.modules.application.MainActivity"
    )
    private const val PREFIX = "${APP_ID}.action."
    const val ACTION_OPEN_TIMER =
        "${PREFIX}OPEN_TIMER"

    const val ACTION_OPEN_NEW_SCREEN =
        "${PREFIX}OPEN_NEW_SCREEN"

    const val ACTION_OPEN_APP =
        "${PREFIX}OPEN_APP"

    private const val EXTRA_TIMER_ID = "timer_id"

    const val EXTRA_APP_INTENT_ACTION = "app_intent_action"

    fun openTimer(
        context: Context,
        timerId: Long,
    ) = Intent(context, MainActivity::class.java).apply {
        action = ACTION_OPEN_TIMER
        putExtra(EXTRA_TIMER_ID, timerId)
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    fun openApp(
        context: Context,
    ) = Intent(context, MainActivity::class.java).apply {
        action = ACTION_OPEN_APP
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    fun openAppPendingIntent(
        context: Context,
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            ACTION_OPEN_APP.hashCode(),
            openApp(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

    fun openTimerPendingIntent(
        context: Context,
        timerId: Long,
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            timerId.hashCode(),
            openTimer(context, timerId),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

    fun openAppLaunchActionExtras() =
        mapOf(
            EXTRA_APP_INTENT_ACTION to
                    ActionBuilders.stringExtra(ACTION_OPEN_APP)
        )

    fun openNewScreenLaunchActionExtras() =
        mapOf(
            EXTRA_APP_INTENT_ACTION to
                    ActionBuilders.stringExtra(ACTION_OPEN_NEW_SCREEN)
        )

    fun openAppClickable(id: String = ACTION_OPEN_APP) =
        ModifiersBuilders.Clickable.Builder()
            .setId(id)
            .setOnClick(
                ActionBuilders.launchAction(
                    MAIN_ACTIVITY_COMPONENT_NAME,
                    openAppLaunchActionExtras()
                )
            )
            .build()

    fun openNewScreenClickable(id: String = ACTION_OPEN_NEW_SCREEN) =
        ModifiersBuilders.Clickable.Builder()
            .setId(id)
            .setOnClick(
                ActionBuilders.launchAction(
                    MAIN_ACTIVITY_COMPONENT_NAME,
                    openNewScreenLaunchActionExtras()
                )
            )
            .build()

    fun parse(intent: Intent): AppIntent? =
        when (intent.action ?: intent.getStringExtra(EXTRA_APP_INTENT_ACTION)) {
            ACTION_OPEN_TIMER -> {
                val id = intent.getLongExtra(EXTRA_TIMER_ID, -1)
                if (id == -1L) {
                    null
                } else {
                    AppIntent.OpenTimer(id)
                }
            }

            ACTION_OPEN_NEW_SCREEN -> AppIntent.OpenNewScreen
            ACTION_OPEN_APP -> AppIntent.OpenApp

            else -> null
        }
}