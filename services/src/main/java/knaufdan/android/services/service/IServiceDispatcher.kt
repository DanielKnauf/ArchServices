package knaufdan.android.services.service

import android.app.Service
import android.os.Bundle
import kotlin.reflect.KClass

interface IServiceDispatcher {

    fun <S : Service> startService(
        serviceClass: KClass<S>,
        bundle: Bundle?
    )

    fun <S : Service> stopService(serviceClass: KClass<S>)
}
