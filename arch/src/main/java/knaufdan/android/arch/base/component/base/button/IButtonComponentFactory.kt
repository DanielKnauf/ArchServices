package knaufdan.android.arch.base.component.base.button

interface IButtonComponentFactory {
    fun get(config: ButtonConfig): IButtonComponent
}
