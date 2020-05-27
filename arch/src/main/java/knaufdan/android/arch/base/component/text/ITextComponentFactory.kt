package knaufdan.android.arch.base.component.text

interface ITextComponentFactory {
    fun create(textConfig: TextConfig): ITextComponent
}
