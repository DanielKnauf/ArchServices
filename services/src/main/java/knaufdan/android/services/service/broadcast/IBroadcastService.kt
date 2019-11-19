package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver

interface IBroadcastService {

    /**
     * Registers the [ActionBroadcastReceiver] at the [LocalBroadcastManager]
     * and adds the [ActionBroadcastReceiver.getSupportedActions] to the [IntentFilter].
     */
    fun registerLocalBroadcastReceiver(actionBroadcastReceiver: ActionBroadcastReceiver)

    fun unregisterLocalBroadcastReceiver(broadcastReceiver: BroadcastReceiver)
}
