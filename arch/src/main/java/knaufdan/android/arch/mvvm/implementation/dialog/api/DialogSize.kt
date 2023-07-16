package knaufdan.android.arch.mvvm.implementation.dialog.api

enum class DialogSize {
    FULL_SCREEN,
    FULL_WIDTH,
    WRAP_CONTENT;

    companion object {

        fun String?.toDialogSize() =
            entries
                .firstOrNull { style -> style.name.equals(this, true) }
                ?: FULL_SCREEN
    }
}
