package knaufdan.android.arch.base.component

interface IComponentViewModel {
    /**
     * Notifies about the attachment to parent view.
     */
    fun onAttach(): Unit = Unit

    /**
     * Notifies about the detachment from parent view.
     */
    fun onDetach(): Unit = Unit
}
