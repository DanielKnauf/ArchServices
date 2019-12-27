package knaufdan.android.arch.mvvm.implementation

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle
import knaufdan.android.arch.navigation.FragmentContainer

internal sealed class Config(
    @LayoutRes val layoutRes: Int,
    val viewModelKey: Int,
    @StringRes val titleRes: Int
) {
    internal class ActivityConfig(
        layoutRes: Int,
        viewModelKey: Int,
        titleRes: Int,
        val fragmentSetup: Pair<FragmentContainer, BaseFragment<out BaseViewModel>?>?
    ) : Config(
        layoutRes,
        viewModelKey,
        titleRes
    )

    internal class FragmentConfig(
        layoutRes: Int,
        viewModelKey: Int,
        titleRes: Int
    ) : Config(
        layoutRes,
        viewModelKey,
        titleRes
    )

    internal class DialogConfig(
        layoutRes: Int,
        viewModelKey: Int,
        titleRes: Int,
        val dialogStyle: DialogStyle = DialogStyle.FULL_WIDTH
    ) : Config(
        layoutRes,
        viewModelKey,
        titleRes
    )
}
