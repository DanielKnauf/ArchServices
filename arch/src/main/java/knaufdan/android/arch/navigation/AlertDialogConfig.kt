package knaufdan.android.arch.navigation

import androidx.annotation.StringRes

data class AlertDialogConfig(
    @StringRes val title: Int,
    @StringRes val message: Int,
    @StringRes val buttonPositive: Int,
    @StringRes val buttonNegative: Int,
    val isCancelable: Boolean
)
