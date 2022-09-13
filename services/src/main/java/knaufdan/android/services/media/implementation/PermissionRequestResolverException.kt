package knaufdan.android.services.media.implementation

import knaufdan.android.services.permission.IPermissionRequestResolver

object PermissionRequestResolverException : Throwable() {
    override val message: String
        get() = "Context not implementing ${IPermissionRequestResolver::class.simpleName}"
}
