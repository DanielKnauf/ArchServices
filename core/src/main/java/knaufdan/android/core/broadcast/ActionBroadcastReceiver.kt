package knaufdan.android.core.broadcast

import android.content.BroadcastReceiver

typealias Action = String

abstract class ActionBroadcastReceiver(protected val forActions: List<Action>) :
    BroadcastReceiver() {

    /**
     * @return a list of [Action] for which the [BroadcastReceiver] should be registered.
     */
    fun getSupportedActions(): List<Action> = forActions
}
