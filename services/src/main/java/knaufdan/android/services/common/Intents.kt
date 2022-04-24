package knaufdan.android.services.common

import android.app.PendingIntent
import android.os.Build

val mutableFlags: Int by lazy {
    if (Build.VERSION.SDK_INT >= 31) {
        PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }
}

val immutableFlags: Int by lazy {
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
}
