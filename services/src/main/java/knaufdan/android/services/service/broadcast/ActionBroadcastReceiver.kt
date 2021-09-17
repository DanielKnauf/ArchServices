package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver

typealias Action = String

/**
 * @param forActions list of [Action] for which the [BroadcastReceiver] should be registered.
 */
abstract class ActionBroadcastReceiver(val forActions: List<Action>) : BroadcastReceiver()
