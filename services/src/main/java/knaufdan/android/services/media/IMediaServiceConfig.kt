package knaufdan.android.services.media

import androidx.core.content.FileProvider

interface IMediaServiceConfig {

    /**
     * Updates the [FileProvider] authority used by the [IMediaService].
     *
     * @param authority authority of a [FileProvider] defined in a <provider> element in your app's
     * manifest.
     */
    fun setFileProviderAuthority(authority: String)
}
