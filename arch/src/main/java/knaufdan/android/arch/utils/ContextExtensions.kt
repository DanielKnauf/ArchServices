package knaufdan.android.arch.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.LifecycleOwner

internal tailrec fun Context?.findLifecycleOwner(): LifecycleOwner? =
    if (this is LifecycleOwner) this
    else (this as? ContextWrapper)?.baseContext?.findLifecycleOwner()
