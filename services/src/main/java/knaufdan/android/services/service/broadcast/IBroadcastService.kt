package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.reflect.KClass

/**
 * [IBroadcastService] is an injectable service providing functionality for sending out Intents to
 * [BroadcastReceiver]s.
 */
interface IBroadcastService {

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
     * Sends an intent to all interested [BroadcastReceiver]s.
     *
     * @param action [IntentAction] to be performed
     * @param configure extension function block to manipulate the created Intent
     */
    fun sendBroadcast(
        action: IntentAction,
        configure: Intent.() -> Unit
    )
}
