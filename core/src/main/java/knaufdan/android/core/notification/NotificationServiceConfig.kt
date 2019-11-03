package knaufdan.android.core.notification

import android.os.Build
import android.util.Log

class NotificationServiceConfig : INotificationServiceConfig {
    var channelId: String = ""
    var channelName: String = ""
    var channelDescription: String = ""
    var channelImportance: Int = 3
    var isVibrationEnabled: Boolean = false
    var isAutoCancelEnabled: Boolean = false

    override fun setNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String,
        channelImportance: Int
    ) {
        this.channelId = channelId
        this.channelName = channelName
        this.channelDescription = channelDescription
        if (channelImportance in 0..5) {
            this.channelImportance = channelImportance
        }
    }

    override fun setVibration(enabled: Boolean) {
        this.isVibrationEnabled = enabled
    }

    override fun setAutoCancel(enabled: Boolean) {
        this.isAutoCancelEnabled = enabled
    }

    fun isValid(): Boolean {
        val needAdditionalChannelInfo = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

        return when {
            channelId.isBlank() -> {
                Log.e(
                    this::class.simpleName,
                    "ChannelId is missing. Add channelId via setNotificationChannel()"
                )
                false
            }
            needAdditionalChannelInfo && channelName.isBlank() -> {
                Log.e(
                    this::class.simpleName,
                    "ChannelName is missing : $channelName. It is needed since Andrpid O, add channelName via setNotificationChannel()"
                )
                false
            }
            needAdditionalChannelInfo && channelDescription.isBlank() -> {
                Log.e(
                    this::class.simpleName,
                    "ChannelDescription is missing : $channelDescription. It is needed since Andrpid O, add channelDescription via setNotificationChannel()"
                )
                false
            }

            else -> true
        }
    }

    companion object {
        val EMPTY = NotificationServiceConfig()
    }
}
