package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver

/**
 * @param forActions list of [IntentAction] for which the [ActionBroadcastReceiver] is registered.
 */
abstract class ActionBroadcastReceiver(val forActions: List<IntentAction>) : BroadcastReceiver()
