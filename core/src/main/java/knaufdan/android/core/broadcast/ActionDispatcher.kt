package knaufdan.android.core.broadcast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class ActionDispatcher<E : Enum<E>>(
    forActions: Array<E>,
    private val actor: (action: Action, extras: Bundle?) -> Unit
) : ActionBroadcastReceiver(forActions.map { action -> action.name }) {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        intent.action?.evaluateAction(intent.extras)
            ?: logError("- no action set for this intent -")
    }

    private fun Action.evaluateAction(extras: Bundle?) {
        if (forActions.contains(this)) actor(this, extras)
        else logError("- action $this not set for this receiver -")
    }

    private fun logError(msg: String) {
        Log.e(this.javaClass.simpleName, msg)
    }
}
