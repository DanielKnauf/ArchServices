package knaufdan.android.services.service.broadcast.implementation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import knaufdan.android.services.service.broadcast.Action
import knaufdan.android.services.service.broadcast.ActionBroadcastReceiver

class ActionDispatcher<E : Enum<E>>(
    forActions: Array<E>,
    private val actor: (action: Action, extras: Bundle?) -> Unit
) : ActionBroadcastReceiver(forActions.map(Enum<E>::name)) {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        intent.action
            ?.evaluateAction(intent.extras)
            ?: logError("- Intent has no action. Intent == $intent")
    }

    private fun Action.evaluateAction(extras: Bundle?) {
        if (forActions.contains(this)) actor(this, extras)
        else logError("- action $this not registered for this receiver.")
    }

    private fun logError(msg: String) = Log.e(javaClass.simpleName, msg)
}
