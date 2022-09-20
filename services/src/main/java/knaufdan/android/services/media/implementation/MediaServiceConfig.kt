package knaufdan.android.services.media.implementation

import knaufdan.android.services.media.IMediaServiceConfig

class MediaServiceConfig : IMediaServiceConfig {

    val authority: String
        get() = userAuthority

    private var userAuthority = ""

    override fun setFileProviderAuthority(authority: String) {
        userAuthority = authority
    }

    fun validate(): Boolean = authority.isNotBlank()

    companion object {
        internal val EMPTY: MediaServiceConfig by lazy(::MediaServiceConfig)
    }
}
