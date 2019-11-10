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
import javax.sql.DataSource

@BindingAdapter(value = ["backgroundResource"])
fun View.bindBackgroundResource(@DrawableRes background: Int) {
    if (background != -1) setBackground(context.getDrawable(background))
}

@BindingAdapter(value = ["number"])
fun TextView.bindNumber(number: Int?) {
    text = number?.toString() ?: ""
}

@BindingAdapter(value = ["bindableOne"])
fun <DataSource>ViewGroup.bindDataSource(bindableOne: BindableElement<DataSource>) {
    val context = context

    val inflater = LayoutInflater.from(context)

    try {
        DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                bindableOne.getLayoutRes(),
                this,
                false
        ).apply {
            setVariable(bindableOne.getBindingKey(), bindableOne.getDataSource())
            if (context is LifecycleOwner) lifecycleOwner = context
            this@bindDataSource.addView(root)
        }
    } catch (e: Resources.NotFoundException) {
        Log.e("BindingAdapters", "LayoutRes could not be found and no binding was generated in $context")
    }
}


//TODO: FIND BETTER NAME!


@BindingAdapter(value = ["bindable"])
fun <DataSource> ViewGroup.bindRecyclerView(bindable: BindableElement<List<DataSource>>) {
    val context = context

    RecyclerView(context).apply {
        layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutManager = LinearLayoutManager(context)
        adapter = BindableAdapter(bindable)

        this@bindRecyclerView.addView(this)
    }
}
