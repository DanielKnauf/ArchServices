package knaufdan.android.arch.base.component.base.text

interface ITextComponentFactory {
    fun get(textConfig: TextConfig): ITextComponent
}
