package knaufdan.android.arch.mvvm.implementation.dialog.api

data class DialogStyle(
    val dialogSize: DialogSize,
    val dialogAnimation: DialogAnimation
) {
    companion object {
        val DEFAULT by lazy {
            DialogStyle(
                dialogSize = DialogSize.FULL_WIDTH,
                dialogAnimation = DialogAnimation.SLIDE_IN_OUT
            )
        }
    }
}
