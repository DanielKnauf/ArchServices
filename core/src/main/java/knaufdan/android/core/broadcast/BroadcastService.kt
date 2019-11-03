package knaufdan.android.core.broadcast

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import javax.inject.Inject
import knaufdan.android.core.ContextProvider

class BroadcastService @Inject constructor(private val contextProvider: ContextProvider) :
    IBroadcastService {

    private val localBroadcastManager: LocalBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(contextProvider.context)
    }

    override fun registerLocalBroadcastReceiver(actionBroadcastReceiver: ActionBroadcastReceiver) {
        IntentFilter()
            .apply {
                for (action: Action in actionBroadcastReceiver.getSupportedActions()) {
                    addAction(action)
                }

                localBroadcastManager.registerReceiver(actionBroadcastReceiver, this)
            }
    }

    override fun unregisterLocalBroadcastReceiver(broadcastReceiver: BroadcastReceiver) {
        localBroadcastManager.unregisterReceiver(broadcastReceiver)
    }
}
