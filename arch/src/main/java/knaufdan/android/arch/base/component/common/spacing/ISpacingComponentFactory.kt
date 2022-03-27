package knaufdan.android.arch.base.component.common.spacing

interface ISpacingComponentFactory {

    fun get(config: SpacingConfig): ISpacingComponent
}
