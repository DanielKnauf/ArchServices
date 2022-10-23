package knaufdan.android.core.common.extensions

fun Int.toStringWithZeros(): String =
    when (this < 10) {
        true -> "0$this"
        false -> "$this"
    }
