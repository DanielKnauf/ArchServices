package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.reflect.KClass

/**
 * [IBroadcastService] is an injectable service providing functionality for (un-)registering
 * [BroadcastReceiver] at the [LocalBroadcastManager] and sending out Intents to [BroadcastReceiver].
 */
interface IBroadcastService {

    /**
     * Registers a [ActionBroadcastReceiver] at the LocalBroadcastManager.
     * [ActionBroadcastReceiver.forActions] defines for which actions the [receiver] is registered.
     *
     * @param receiver [ActionBroadcastReceiver] to handle the broadcast
     */
    @Deprecated("LocalBroadcast is no longer supported by Google, will be removed with 0.11.0")
    fun registerLocalBroadcastReceiver(receiver: ActionBroadcastReceiver)

    /**
     * Unregisters [BroadcastReceiver] from the LocalBroadcastManager.
     *
     * @param receiver [BroadcastReceiver] to unregister
     */
    @Deprecated("LocalBroadcast is no longer supported by Google, will be removed with 0.11.0")
    fun unregisterLocalBroadcastReceiver(receiver: BroadcastReceiver)

    /**
     * Sends an intent to [receiver].
     *
     * @param receiver [KClass] to handle the intent
     * @param action [IntentAction] to perform by the [receiver]
     * @param configure extension function block to manipulate the created Intent
     */
    fun <T : BroadcastReceiver> sendBroadcast(
        receiver: KClass<T>,
        action: IntentAction,
        configure: Intent.() -> Unit = {}
    )

    /**
     * Sends an intent to all interested BroadcastReceivers.
     *
     * @param action [IntentAction] to be performed
     * @param configure extension function block to manipulate the created Intent
     */
    fun sendBroadcast(
        action: IntentAction,
        configure: Intent.() -> Unit
    )
}
