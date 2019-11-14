package knaufdan.android.services.userinteraction.audio

import androidx.annotation.RawRes

typealias AudioRes = Int

interface IAudioService {

    fun play(@RawRes audioRes: AudioRes)

    fun release(@RawRes audioRes: AudioRes)
}
