package knaufdan.android.services.userinteraction.notification.api

import android.app.Activity
import kotlin.reflect.KClass

data class NotificationInteraction(
    val activityTarget: KClass<out Activity>?,
    val actions: List<NotificationAction> = emptyList()
) {
    companion object {
        val EMPTY by lazy {
            NotificationInteraction(
                activityTarget = null,
                actions = emptyList()
            )
        }
    }
}
