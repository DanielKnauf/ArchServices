package knaufdan.android.arch.base.component.base.info

interface IInfoComponentFactory {
    fun get(infoConfig: InfoConfig): IInfoComponent
}
