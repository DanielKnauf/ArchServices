package knaufdan.android.arch.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.addStringToBundle(
    key: String,
    value: String
) {
    val args = this.arguments ?: Bundle()
    args.putString(key, value)
    this.arguments = args
}
