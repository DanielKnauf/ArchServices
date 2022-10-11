package knaufdan.android.arch.databinding.view

import android.animation.LayoutTransition
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

@BindingAdapter(
    "enableLayoutTransitionType"
)
fun ViewGroup.bindEnableLayoutTransitionType(type: LayoutTransitionType) {
    layoutTransition.enableTransitionType(type.toAndroidTransitionType())
}

@BindingAdapter(
    "disableLayoutTransitionType"
)
fun ViewGroup.bindDisableLayoutTransitionType(type: LayoutTransitionType) {
    layoutTransition.disableTransitionType(type.toAndroidTransitionType())
}

enum class LayoutTransitionType {
    CHANGING;

    fun toAndroidTransitionType(): Int =
        when (this) {
            CHANGING -> LayoutTransition.CHANGING
        }
}
