package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver
import android.content.Intent
import kotlin.reflect.KClass

interface IBroadcastService {

    /**
     * Registers the [ActionBroadcastReceiver] at the [LocalBroadcastManager]
     * and adds the [ActionBroadcastReceiver.getSupportedActions] to the [IntentFilter].
     */
    fun registerLocalBroadcastReceiver(actionBroadcastReceiver: ActionBroadcastReceiver)

    fun unregisterLocalBroadcastReceiver(broadcastReceiver: BroadcastReceiver)

    fun <T : BroadcastReceiver> sendBroadcast(
        receiver: KClass<T>,
        action: IntentAction
    )

    fun sendBroadcast(
        action: IntentAction,
        configure: Intent.() -> Unit
    )
}
