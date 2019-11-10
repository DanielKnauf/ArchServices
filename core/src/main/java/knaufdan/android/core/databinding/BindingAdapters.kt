package knaufdan.android.core.databinding

import android.content.res.Resources
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

@BindingAdapter(value = ["element"])
fun ViewGroup.bindElement(element: BindableElement<*>) {
    val context = context

    if (element.getDataSource() is List<*>) {
        bindRecyclerView(listElement = element.toListElement())
        return
    }

    val inflater = LayoutInflater.from(context)

    try {
        DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                element.getLayoutRes(),
                this,
                false
        ).apply {
            setVariable(element.getBindingKey(), element.getDataSource())
            if (context is LifecycleOwner) lifecycleOwner = context
            this@bindElement.addView(root)
        }
    } catch (e: Resources.NotFoundException) {
        Log.e(".bindDataSource()", "LayoutRes could not be found. No binding was generated in $context")
    }
}

private fun <DataSource> ViewGroup.bindRecyclerView(listElement: BindableElement<List<DataSource>>) {
    val context = context

    RecyclerView(context).apply {
        layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutManager = LinearLayoutManager(context)
        adapter = BindableAdapter(
                dataSources = listElement.getDataSource(),
                layoutRes = listElement.getLayoutRes(),
                bindingKey = listElement.getBindingKey()
        )

        this@bindRecyclerView.addView(this)
    }
}

private fun BindableElement<*>.toListElement() = object : BindableElement<List<*>> {
    override fun getLayoutRes() = this@toListElement.getLayoutRes()

    override fun getBindingKey() = this@toListElement.getBindingKey()

    override fun getDataSource() = this@toListElement.getDataSource() as List<*>
}
