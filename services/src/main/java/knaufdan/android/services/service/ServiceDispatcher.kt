package knaufdan.android.services.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import knaufdan.android.core.IContextProvider
import javax.inject.Inject
import kotlin.reflect.KClass

internal class ServiceDispatcher @Inject constructor(private val contextProvider: IContextProvider) : IServiceDispatcher {

    override fun <S : Service> startService(
        serviceClass: KClass<S>,
        bundle: Bundle?
    ) {
        contextProvider.getContext().apply {
            val intent = Intent(
                this,
                serviceClass.java
            )
            bundle?.apply {
                intent.putExtras(this)
            }

            startService(intent)
        }
    }

    override fun <S : Service> stopService(serviceClass: KClass<S>) {
        val context = contextProvider.getContext()
        context.stopService(Intent(context, serviceClass.java))
    }
}
