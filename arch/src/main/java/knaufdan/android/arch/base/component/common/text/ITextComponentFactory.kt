package knaufdan.android.arch.base.component.common.text

interface ITextComponentFactory {
    fun create(textConfig: TextConfig): ITextComponent
}
