package knaufdan.android.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.core.IContextProvider

@Singleton
class AudioService @Inject constructor(private val contextProvider: IContextProvider) :
        IAudioService {

    private val mediaPlayers = mutableMapOf<Int, MediaPlayer>()

    private val audioManager
        get() = contextProvider.getContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager

    override fun play(audioRes: AudioRes) {
        val mediaPlayer = mediaPlayers.getOrPut(audioRes) {
            audioRes.createMediaPlayer()
        }

        if (mediaPlayer.isPlaying) {
            return
        }

        val audioFocusChangeListener = mediaPlayer.createAudioFocusChangeListener()

        with(audioManager) {
            if (isMusicActive) {
                val res: Int =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            requestAudioFocus(
                                    AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK).run {
                                        setAudioAttributes(audioAttributes)
                                        setOnAudioFocusChangeListener(audioFocusChangeListener)
                                        build()
                                    }
                            )
                        } else {
                            requestAudioFocus(
                                    audioFocusChangeListener,
                                    AudioManager.STREAM_MUSIC,
                                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
                            )
                        }

                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer.start()
                }
            } else {
                mediaPlayer.start()
            }
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun release(audioRes: AudioRes) {
        mediaPlayers[audioRes]?.run {
            if (isPlaying) {
                Handler().postDelayed({ release(audioRes) }, 500)
            } else {
                release()
                mediaPlayers.remove(audioRes)
            }
        }
    }

    private fun MediaPlayer.releaseAudioFocus() {
        if (!isPlaying) {
            with(audioManager) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    abandonAudioFocusRequest(
                            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK).run {
                                setAudioAttributes(audioAttributes)
                                build()
                            }
                    )
                } else {
                    abandonAudioFocus(createAudioFocusChangeListener())
                }
            }
        }
    }

    private fun AudioRes.createMediaPlayer() =
            MediaPlayer.create(
                    contextProvider.getContext(),
                    this
            ).apply {
                setAudioAttributes(audioAttributes)
                setOnCompletionListener { releaseAudioFocus() }
            }

    private fun MediaPlayer.createAudioFocusChangeListener() =
            AudioManager.OnAudioFocusChangeListener { focusChange ->
                // loss of audio focus is indicated by a negative value
                if (focusChange < 0) this.stop()
            }

    companion object {
        private val audioAttributes by lazy {
            AudioAttributes.Builder().run {
                setUsage(AudioAttributes.USAGE_MEDIA)
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                build()
            }
        }
    }
}
