package knaufdan.android.arch.base.component.addition.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

class ComponentFragmentFactory(
    private val component: IComponent<IComponentViewModel>
) : FragmentFactory() {
    override fun instantiate(
        classLoader: ClassLoader,
        className: String
    ): Fragment {
        return ComponentFragment(
            component = component
        )
    }

    fun instantiate(): ComponentFragment =
        instantiate(
            this::class.java.classLoader!!,
            ComponentFragment::class.java.name
        ) as ComponentFragment
}
