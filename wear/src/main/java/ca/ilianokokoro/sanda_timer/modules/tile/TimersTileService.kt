package ca.ilianokokoro.sanda_timer.modules.tile

import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.layout.androidImageResource
import androidx.wear.protolayout.layout.imageResource
import androidx.wear.protolayout.material3.CardDefaults.filledTonalCardColors
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.icon
import androidx.wear.protolayout.material3.iconEdgeButton
import androidx.wear.protolayout.material3.materialScopeWithResources
import androidx.wear.protolayout.material3.primaryLayout
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.material3.titleCard
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.Material3TileService
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import ca.ilianokokoro.sanda_timer.core.R
import ca.ilianokokoro.sanda_timer.core.helpers.IntentHelper

class TimersTileService : Material3TileService() {
    override suspend fun MaterialScope.tileResponse(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        return TileBuilders.Tile.Builder()
            .setTileTimeline(
                TimelineBuilders.Timeline.fromLayoutElement(
                    materialScopeWithResources(
                        context,
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
                                    title = { text(context.getString(R.string.add_new_timer).layoutString) },
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
    }


}
