package knaufdan.android.services.userinteraction.notification.api

import android.app.Activity
import kotlin.reflect.KClass

data class NotificationInteraction(
    val activityTarget: KClass<out Activity>? = null,
    val actions: List<NotificationAction> = emptyList()
)
