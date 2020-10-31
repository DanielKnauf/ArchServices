package knaufdan.android.arch.base.component.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import java.util.WeakHashMap

class ComponentFragment(
    component: IComponent<IComponentViewModel>
) : Fragment() {
    private val viewModel = component.getDataSource()
    private val layoutRes = component.getLayoutRes()
    private val viewModelKey = component.getBindingKey()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        bindings.getOrPut(viewModel) {
            DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                layoutRes,
                container,
                false
            ).apply {
                setVariable(viewModelKey, viewModel)
            }
        }.run {
            lifecycleOwner = this@ComponentFragment
            executePendingBindings()
            root
        }

    override fun onResume() {
        super.onResume()
        viewModel.onAttach()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onDetach()
    }

    companion object {
        private val bindings: MutableMap<IComponentViewModel, ViewDataBinding> = WeakHashMap()
    }
}
