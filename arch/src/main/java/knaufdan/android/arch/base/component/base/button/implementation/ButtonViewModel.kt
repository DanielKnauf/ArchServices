package knaufdan.android.arch.base.component.base.button.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.base.button.ButtonConfig
import knaufdan.android.core.resources.IResourceProvider

class ButtonViewModel(
    private val config: ButtonConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {
    val containerLayoutWidth = config.layoutWidth
    val containerLayoutHeight = config.layoutHeight
    val width = resourceProvider.getDimension(config.width)
    val height = resourceProvider.getDimension(config.height)
    val background = config.background
    val marginTop = resourceProvider.getDimension(config.marginTop)

    fun onButtonClicked() {
        config.onButtonClicked()
    }
}
