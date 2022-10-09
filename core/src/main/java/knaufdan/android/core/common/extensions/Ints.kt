package knaufdan.android.core.common.extensions

fun Int.addZero(): String =
    if (this < 10) "0$this"
    else "$this"
