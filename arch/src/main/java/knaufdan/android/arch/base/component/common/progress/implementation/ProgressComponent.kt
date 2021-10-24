package knaufdan.android.arch.base.component.common.progress.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.common.progress.IProgressComponent
import knaufdan.android.arch.base.component.implementation.EmptyComponentViewModel

class ProgressComponent : IProgressComponent {

    override fun getLayoutRes(): LayoutRes = R.layout.arch_progress

    override fun getDataSource(): EmptyComponentViewModel = EmptyComponentViewModel
}
