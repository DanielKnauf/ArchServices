package knaufdan.android.arch.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner

val Fragment.viewModelStoreOwner: ViewModelStoreOwner
    get() = requireActivity()
