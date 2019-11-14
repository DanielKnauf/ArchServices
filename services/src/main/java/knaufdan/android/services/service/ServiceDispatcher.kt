package knaufdan.android.services.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import javax.inject.Inject
import knaufdan.android.core.IContextProvider
import kotlin.reflect.KClass

class ServiceDispatcher @Inject constructor(private val contextProvider: IContextProvider) : IServiceDispatcher {

    override fun <S : Service> startService(
        serviceClass: KClass<S>,
        bundle: Bundle?
    ) {
        val context = contextProvider.getContext()
        val intent = Intent(context, serviceClass.java)

        bundle?.let {
            intent.putExtras(it)
        }

        context.startService(intent)
    }

    override fun <S : Service> stopService(serviceClass: KClass<S>) {
        val context = contextProvider.getContext()
        context.stopService(Intent(context, serviceClass.java))
    }
}
