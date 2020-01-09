package knaufdan.android.arch.databinding

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["backgroundResource"])
fun View.bindBackgroundResource(@DrawableRes background: Int) {
    if (background != -1) setBackground(context.getDrawable(background))
}

@BindingAdapter(value = ["number"])
fun TextView.bindNumber(number: Int?) {
    text = number?.toString() ?: ""
}

@BindingAdapter(value = ["component"])
fun ViewGroup.bindComponent(component: IComponent<*>?) {
    if (component == null) {
        return
    }

    if (component.getDataSource() is List<*>) {
        component.toListComponent().bindToRecyclerView(parent = this)
    } else {
        component.bindToLinearLayout(parent = this)
    }
}

private fun <DataSource> IComponent<List<DataSource>>.bindToRecyclerView(parent: ViewGroup) {
    val context = parent.context

    RecyclerView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutManager = LinearLayoutManager(context)
        adapter = BindableAdapter(
            dataSources = getDataSource(),
            layoutRes = getLayoutRes(),
            bindingKey = getBindingKey()
        )

        parent.removeAllViewsInLayout()
        parent.addView(this)
    }
}

private fun <DataSource> IComponent<DataSource>.bindToLinearLayout(parent: ViewGroup) {
    val context = parent.context

    try {
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            getLayoutRes(),
            parent,
            false
        ).apply {
            setVariable(getBindingKey(), getDataSource())
            if (context is LifecycleOwner) lifecycleOwner = context
            parent.removeAllViewsInLayout()
            parent.addView(root)
        }
    } catch (e: Throwable) {
        Log.e(
            ".bindToLinearLayout()",
            "LayoutRes could not be found. No binding was generated for $parent in $context"
        )
    }
}

private fun IComponent<*>.toListComponent() =
    object : IComponent<List<*>> {
        override fun getLayoutRes() = this@toListComponent.getLayoutRes()

        override fun getBindingKey() = this@toListComponent.getBindingKey()

        override fun getDataSource() = this@toListComponent.getDataSource() as List<*>
    }
