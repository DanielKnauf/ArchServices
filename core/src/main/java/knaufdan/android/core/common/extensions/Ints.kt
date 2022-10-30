package knaufdan.android.core.common.extensions

fun Int.toStringWithLeadingZero(): String =
    when (this < 10) {
        true -> "0$this"
        false -> "$this"
    }
