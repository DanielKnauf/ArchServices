package knaufdan.android.services.media.implementation

import knaufdan.android.services.media.IMediaRequestResolver

object MediaRequestResolverException : Throwable() {
    override val message: String
        get() = "Context does not implementing ${IMediaRequestResolver::class.simpleName}"
}
