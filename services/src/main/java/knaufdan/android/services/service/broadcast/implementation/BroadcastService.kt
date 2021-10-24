package knaufdan.android.services.service.broadcast.implementation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import knaufdan.android.core.IContextProvider
import knaufdan.android.services.service.broadcast.ActionBroadcastReceiver
import knaufdan.android.services.service.broadcast.IBroadcastService
import knaufdan.android.services.service.broadcast.IntentAction
import kotlin.reflect.KClass

internal class BroadcastService(
    private val contextProvider: IContextProvider
) : IBroadcastService {

    private val context: Context
        get() = contextProvider.getContext()

    private val localBroadcastManager: LocalBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(context)
    }

    override fun registerLocalBroadcastReceiver(receiver: ActionBroadcastReceiver) {
        IntentFilter()
            .apply {
                receiver.forActions.forEach(::addAction)

                localBroadcastManager.registerReceiver(receiver, this)
            }
    }

    override fun unregisterLocalBroadcastReceiver(receiver: BroadcastReceiver) =
        localBroadcastManager.unregisterReceiver(receiver)

    override fun <T : BroadcastReceiver> sendBroadcast(
        receiver: KClass<T>,
        action: IntentAction
    ) =
        context.sendBroadcast(
            Intent(
                context,
                receiver.java
            ).setAction(action)
        )

    override fun sendBroadcast(
        action: IntentAction,
        configure: Intent.() -> Unit
    ) = context.sendBroadcast(Intent(action).apply(configure))
}
