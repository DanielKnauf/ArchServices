package knaufdan.android.services.userinteraction.notification.api

import android.app.Activity
import knaufdan.android.services.common.Constants.Intent.ACTION_OPEN_APP
import kotlin.reflect.KClass

data class NotificationInteraction(
    val activityTarget: KClass<out Activity>?,
    val activityTargetAction: String = ACTION_OPEN_APP,
    val actions: List<NotificationAction> = emptyList()
) {
    companion object {

        val EMPTY by lazy {
            NotificationInteraction(
                activityTarget = null,
                activityTargetAction = "",
                actions = emptyList()
            )
        }
    }
}
