package knaufdan.android.services.service.broadcast.implementation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.services.service.broadcast.IBroadcastService
import knaufdan.android.services.service.broadcast.IntentAction
import kotlin.reflect.KClass

internal class BroadcastService(
    private val contextProvider: IContextProvider
) : IBroadcastService {

    private val context: Context
        get() = contextProvider.getContext()

    override fun <T : BroadcastReceiver> sendBroadcast(
        receiver: KClass<T>,
        action: IntentAction,
        configure: Intent.() -> Unit
    ) =
        context.sendBroadcast(
            Intent(
                context,
                receiver.java
            ).setAction(action)
                .apply(configure)
        )

    override fun sendBroadcast(
        action: IntentAction,
        configure: Intent.() -> Unit
    ) = context.sendBroadcast(Intent(action).apply(configure))
}
