package knaufdan.android.core.common.extensions

fun Int.toStringWithZeros(): String =
    if (this < 10) "0$this"
    else "$this"
