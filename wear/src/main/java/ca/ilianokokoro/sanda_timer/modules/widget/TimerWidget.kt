@file:SuppressLint("RestrictedApi")

package ca.ilianokokoro.sanda_timer.modules.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.remote.creation.compose.action.pendingIntentAction
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.glance.wear.GlanceWearWidget
import androidx.glance.wear.WearWidgetBrush
import androidx.glance.wear.WearWidgetData
import androidx.glance.wear.WearWidgetDocument
import androidx.glance.wear.color
import androidx.glance.wear.core.WearWidgetParams
import androidx.wear.compose.remote.material3.RemoteButton
import androidx.wear.compose.remote.material3.RemoteButtonDefaults
import androidx.wear.compose.remote.material3.RemoteColorScheme
import androidx.wear.compose.remote.material3.RemoteIcon
import androidx.wear.compose.remote.material3.RemoteMaterialTheme
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper

class TimerWidget : GlanceWearWidget() {
    override suspend fun provideWidgetData(
        context: Context,
        params: WearWidgetParams,
    ): WearWidgetData {
        val remoteColorScheme = RemoteColorScheme()
        return WearWidgetDocument(
            background = WearWidgetBrush.color(remoteColorScheme.surfaceContainerLow)
        ) {
            TimerWidgetContent()
        }
    }
}

@RemoteComposable
@Composable
fun TimerWidgetContent() {
    val openAppAction = pendingIntentAction { ctx ->
        IntentHelper.openNewScreenPendingIntent(ctx)
    }

    RemoteBox(
        modifier = RemoteModifier.fillMaxSize(),
        contentAlignment = RemoteAlignment.BottomCenter,
    ) {
        RemoteButton(
            onClick = openAppAction,
            colors = RemoteButtonDefaults.buttonColors().copy(
                containerColor = RemoteMaterialTheme.colorScheme.primary,
                contentColor = RemoteMaterialTheme.colorScheme.onPrimary
            ),
            modifier = RemoteModifier.fillMaxSize()
        ) {
            RemoteIcon(
                Icons.Rounded.Add,
                contentDescription = Icons.Rounded.Add.name.rs
            )
        }
    }
}
