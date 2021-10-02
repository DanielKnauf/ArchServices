package knaufdan.android.arch.base.component.addition.recyclerview.binding

import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.addition.recyclerview.RecyclerViewSnapBehavior
import knaufdan.android.arch.base.component.addition.recyclerview.RecyclerViewSnapBehavior.Companion.toAndroidSnapHelper

fun RecyclerView.setSnapHelper(snapBehavior: RecyclerViewSnapBehavior?) {
    val androidSnapHelper = snapBehavior?.toAndroidSnapHelper() ?: return
    androidSnapHelper.attachToRecyclerView(this)
}
