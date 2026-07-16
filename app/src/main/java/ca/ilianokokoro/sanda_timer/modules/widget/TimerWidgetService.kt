package ca.ilianokokoro.sanda_timer.modules.widget

import androidx.glance.wear.AssociateWithGlanceWearWidget
import androidx.glance.wear.GlanceWearWidget
import androidx.glance.wear.GlanceWearWidgetService


@AssociateWithGlanceWearWidget(TimerWidget::class)
class TimerWidgetService : GlanceWearWidgetService() {
    override val widget: GlanceWearWidget = TimerWidget()
}