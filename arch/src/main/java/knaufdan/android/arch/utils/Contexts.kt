package knaufdan.android.arch.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

internal tailrec fun Context?.findLifecycleOwner(): LifecycleOwner? =
    if (this is LifecycleOwner) this
    else (this as? ContextWrapper)?.baseContext?.findLifecycleOwner()

fun Context.dpToPx(dp: Number): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)

fun Context.getColorCompat(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
