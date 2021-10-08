package knaufdan.android.arch.base.component.common.progress.implementation

import knaufdan.android.arch.base.component.common.progress.IProgressComponent
import knaufdan.android.arch.base.component.common.progress.IProgressComponentFactory

class ProgressComponentFactory : IProgressComponentFactory {

    override fun get(): IProgressComponent = ProgressComponent()
}
