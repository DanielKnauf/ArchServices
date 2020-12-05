package knaufdan.android.arch.base.component.common.text

interface ITextComponentFactory {
    fun get(textConfig: TextConfig): ITextComponent
}
