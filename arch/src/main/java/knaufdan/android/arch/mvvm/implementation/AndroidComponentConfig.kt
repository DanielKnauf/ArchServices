package knaufdan.android.arch.mvvm.implementation

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogSize
import knaufdan.android.arch.navigation.ContainerViewId

internal sealed class AndroidComponentConfig(
    @LayoutRes val layoutRes: Int,
    val viewModelKey: Int,
    @StringRes val activityTitleRes: Int
) {
    internal class ActivityConfig(
        @LayoutRes layoutRes: Int,
        viewModelKey: Int,
        @StringRes activityTitleRes: Int,
        val fragmentSetup: Pair<ContainerViewId, BaseFragment<out BaseFragmentViewModel>?>?
    ) : AndroidComponentConfig(
        layoutRes = layoutRes,
        viewModelKey = viewModelKey,
        activityTitleRes = activityTitleRes
    )

    internal class FragmentConfig(
        @LayoutRes layoutRes: Int,
        viewModelKey: Int,
        @StringRes activityTitleRes: Int
    ) : AndroidComponentConfig(
        layoutRes = layoutRes,
        viewModelKey = viewModelKey,
        activityTitleRes = activityTitleRes
    )

    internal class DialogConfig(
        @LayoutRes layoutRes: Int,
        viewModelKey: Int,
        @StringRes activityTitleRes: Int,
        val dialogSize: DialogSize = DialogSize.FULL_WIDTH
    ) : AndroidComponentConfig(
        layoutRes = layoutRes,
        viewModelKey = viewModelKey,
        activityTitleRes = activityTitleRes
    )
}
