package knaufdan.android.arch.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindableAdapter<DataSource>(
    private val dataSources: List<DataSource>,
    private val layoutRes: LayoutRes,
    private val bindingKey: BindingKey
) : RecyclerView.Adapter<BindableViewHolder<DataSource>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindableViewHolder<DataSource> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRes, parent, false)
        return BindableViewHolder(
            binding = binding,
            bindingKey = bindingKey
        )
    }

    override fun onBindViewHolder(holder: BindableViewHolder<DataSource>, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSources.size
}
