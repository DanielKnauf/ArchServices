package knaufdan.android.arch.mvvm.implementation.dialog.api

data class DialogStyle(
    val dialogSize: DialogSize = DialogSize.FULL_WIDTH,
    val dialogAnimation: DialogAnimation = DialogAnimation.SLIDE_IN_OUT
) {
    companion object {
        val DEFAULT by lazy {
            DialogStyle()
        }
    }
}
