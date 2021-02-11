package knaufdan.android.arch.base.component.common.info

interface IInfoComponentFactory {
    fun get(infoConfig: InfoConfig): IInfoComponent
}
