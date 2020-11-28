package knaufdan.android.arch.mvvm.implementation.dialog

enum class DialogStyle {
    FULL_SCREEN,
    FULL_WIDTH
}

fun String.toDialogStyle() =
    DialogStyle.values().firstOrNull { style ->
        style.name.equals(this, true)
    } ?: DialogStyle.FULL_SCREEN
