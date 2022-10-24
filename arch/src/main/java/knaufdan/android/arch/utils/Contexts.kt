package knaufdan.android.arch.utils

import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

internal tailrec fun Context?.findLifecycleOwner(): LifecycleOwner? =
    when (this is LifecycleOwner) {
        true -> this
        false -> (this as? ContextWrapper)?.baseContext?.findLifecycleOwner()
    }

fun Context.dpToPx(dp: Number): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)

fun Context.getColorCompat(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)
