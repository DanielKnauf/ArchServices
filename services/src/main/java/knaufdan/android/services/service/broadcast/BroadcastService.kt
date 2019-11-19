package knaufdan.android.services.service.broadcast

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import javax.inject.Inject
import knaufdan.android.core.IContextProvider

class BroadcastService @Inject constructor(private val contextProvider: IContextProvider) :
    IBroadcastService {

    private val localBroadcastManager: LocalBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(contextProvider.getContext())
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
