package ca.ilianokokoro.sanda_timer.modules.tile

import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.layout.androidImageResource
import androidx.wear.protolayout.layout.imageResource
import androidx.wear.protolayout.material3.CardDefaults.filledTonalCardColors
import androidx.wear.protolayout.material3.icon
import androidx.wear.protolayout.material3.iconEdgeButton
import androidx.wear.protolayout.material3.materialScopeWithResources
import androidx.wear.protolayout.material3.primaryLayout
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.material3.titleCard
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TileService
import ca.ilianokokoro.sanda_timer.core.R
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper
import com.google.common.util.concurrent.Futures

class TimersTileService : TileService() {
    override fun onTileRequest(requestParams: RequestBuilders.TileRequest) =
        Futures.immediateFuture(
            TileBuilders.Tile.Builder()
                .setTileTimeline(
                    TimelineBuilders.Timeline.fromLayoutElement(
                        materialScopeWithResources(
                            this,
                            requestParams.scope,
                            requestParams.deviceConfiguration
                        ) {
                            primaryLayout(
                                titleSlot = {
                                    text(context.getString(R.string.timer_tile_label).layoutString)
                                },
                                mainSlot = {
                                    titleCard(
                                        // TEMP : show all timers
                                        onClick = IntentHelper.openAppClickable(),
                                        height = expand(),
                                        colors = filledTonalCardColors(),
                                        title = { text(context.getString(R.string.no_timers).layoutString) },
                                        content = { text("Add a new timer".layoutString) },
                                    )
                                },
                                bottomSlot = {
                                    iconEdgeButton(
                                        iconContent = {
                                            icon(
                                                imageResource(
                                                    androidImageResource(
                                                        R.drawable.ic_add
                                                    )
                                                )
                                            )
                                        },
                                        onClick = IntentHelper.openNewScreenClickable()
                                    )
                                }
                            )
                        }
                    )
                )
                .build()
        )
}
