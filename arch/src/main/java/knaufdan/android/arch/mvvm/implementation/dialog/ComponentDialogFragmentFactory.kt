package knaufdan.android.arch.mvvm.implementation.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentFactory
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogStyle

class ComponentDialogFragmentFactory(
    private val component: IComponent<IComponentViewModel>,
    private val dialogStyle: DialogStyle = DialogStyle.DEFAULT
) : FragmentFactory() {
    override fun instantiate(
        classLoader: ClassLoader,
        className: String
    ): DialogFragment {
        return ComponentDialogFragment(
            component = component,
            dialogStyle = dialogStyle
        )
    }

    fun instantiate(): ComponentDialogFragment =
        instantiate(
            this::class.java.classLoader!!,
            ComponentDialogFragment::class.java.name
        ) as ComponentDialogFragment
}
