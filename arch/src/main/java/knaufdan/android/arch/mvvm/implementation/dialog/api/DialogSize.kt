package knaufdan.android.arch.mvvm.implementation.dialog.api

enum class DialogSize {
    FULL_SCREEN,
    FULL_WIDTH,
    WRAP_CONTENT
}

fun String.toDialogSize() =
    DialogSize.values().firstOrNull { style ->
        style.name.equals(this, true)
    } ?: DialogSize.FULL_SCREEN
