package knaufdan.android.arch.mvvm.toolbar.implementation

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

@BindingAdapter("config")
fun MaterialToolbar.bindConfig(config: ToolbarConfig) {
    if (config.isValid().not()) {
        visibility = View.GONE
        return
    }

    menu.clear()

    inflateMenu(config.menuRes)

    setNavigationIcon(config.navigationIcon)

    visibility = if (config.isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("onMenuItemClicked")
fun MaterialToolbar.bindOnMenuItemClicked(listener: Toolbar.OnMenuItemClickListener) {
    setOnMenuItemClickListener(listener)
}

@BindingAdapter("onNavigationIconClicked")
fun MaterialToolbar.bindOnNavigationIconClicked(listener: View.OnClickListener) {
    setNavigationOnClickListener(listener)
}

private fun MaterialToolbar.setNavigationIcon(@DrawableRes iconRes: Int?) {
    navigationIcon = null
    navigationIcon = iconRes?.run {
        ContextCompat.getDrawable(
            context,
            this
        )
    }
}
