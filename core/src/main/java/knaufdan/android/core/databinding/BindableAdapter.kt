package knaufdan.android.core.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindableAdapter<T>(
        private val bindableElement: BindableElement<List<T>>
) : RecyclerView.Adapter<BindableViewHolder<T>>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BindableViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, bindableElement.getLayoutRes(), parent, false)
        return BindableViewHolder(binding, bindableElement.getBindingKey())
    }

    override fun onBindViewHolder(holder: BindableViewHolder<T>, position: Int) {
        holder.bind(bindableElement.getDataSource()[position])
    }

    override fun getItemCount(): Int = bindableElement.getDataSource().size
}
