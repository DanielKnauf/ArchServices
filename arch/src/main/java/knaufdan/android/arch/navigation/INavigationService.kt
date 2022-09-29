package knaufdan.android.arch.navigation

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseFragmentViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogStyle
import kotlin.reflect.KClass

interface INavigationService {

    var containerViewId: Int

    val fragmentManager: FragmentManager?

    val navigationController: NavController?

    @Deprecated("Will be removed with 0.13.0.")
    fun toAlertDialog(
        config: AlertDialogConfig,
        onPositiveButtonClicked: () -> Unit,
        onNegativeButtonClicked: () -> Unit,
        onDismissClicked: () -> Unit = {}
    )

    fun toDestination(directions: NavDirections)

    fun toFragment(
        transaction: IFragmentTransaction,
        containerViewId: Int = this.containerViewId
    )

    /**
     * Starting with Android 30 a query must be included inside the AndroidManifest. Otherwise, the
     * app will not find applications to resolve the implicit intent.
     * - action: android:name="android.intent.action.SENDTO"
     * - data: android:scheme="mailto"
     *
     * See also https://developer.android.com/training/package-visibility
     *
     * @param recipient who will receive the e-mail
     * @param subject which will be added to the mail
     */
    fun toEmail(
        recipient: String,
        subject: String = ""
    )

    fun toWeb(target: IWebTarget)

    @Deprecated("Will be removed with 0.13.0.")
    fun showComponent(
        component: IComponent<IComponentViewModel>,
        addToBackStack: Boolean,
        containerViewId: Int = this.containerViewId
    )

    /**
     * @param fragment [BaseFragment] which will be displayed.
     * @param containerViewId id of viewGroup in which the [fragment] is placed.
     * @param addToBackStack if true the [fragment] is added to the Fragment-Backstack.
     * @param clearBackStack clears backStack before placing [fragment] into the [containerViewId].
     * @param params parameters which should be put in the bundle of the [fragment].
     */
    @Deprecated("Will be removed with 0.13.0.")
    fun goToFragment(
        fragment: BaseFragment<out BaseFragmentViewModel>,
        addToBackStack: Boolean,
        containerViewId: Int = this.containerViewId,
        clearBackStack: Boolean = false,
        vararg params: Pair<String, Any?> = emptyArray()
    )

    /**
     * Iterates over all fragments of the current activity and returns first instance of [fragmentClass].
     *
     * @param fragmentClass which instance should be returned.
     */
    @Deprecated("Will be removed with 0.13.0.")
    fun <T : Fragment> getFragment(
        fragmentClass: KClass<out T>
    ): T?

    /**
     * @param fragment [BaseDialogFragment] which will be displayed.
     * @param dialogStyle [DialogStyle] in which the [fragment] is displayed.
     * @param callback method which is invoked when the [fragment] is dismissed.
     */
    @Deprecated("Will be removed with 0.13.0.")
    fun <ResultType> showDialog(
        fragment: BaseDialogFragment<out BaseFragmentViewModel>,
        dialogStyle: DialogStyle = DialogStyle.DEFAULT,
        callback: ((ResultType?) -> Unit) = {}
    )

    @Deprecated("Will be removed with 0.13.0.")
    fun <ResultType> showDialog(
        component: IComponent<IComponentViewModel>,
        fragmentTag: FragmentTag = "",
        dialogStyle: DialogStyle = DialogStyle.DEFAULT,
        callback: ((ResultType?) -> Unit) = {}
    )

    @Deprecated("Will be removed with 0.13.0.")
    fun showDialog(
        component: IComponent<IComponentViewModel>,
        fragmentTag: FragmentTag = "",
        dialogStyle: DialogStyle = DialogStyle.DEFAULT
    )

    fun showDialog(
        dialog: DialogFragment,
        fragmentTag: FragmentTag = ""
    )

    @Deprecated("Will be removed with 0.13.0.")
    fun dismissDialog(viewModel: BaseFragmentViewModel)

    @Deprecated("Will be removed with 0.13.0.")
    fun <ResultType> dismissDialog(
        viewModel: BaseFragmentViewModel,
        result: ResultType? = null
    )

    @Deprecated("Will be removed with 0.13.0.")
    fun dismissDialog(fragmentTag: String)

    @Deprecated("Will be removed with 0.13.0.")
    fun <ResultType> dismissDialog(
        fragmentTag: String,
        result: ResultType?
    )

    fun showToast(
        @StringRes text: Int,
        duration: Int = Toast.LENGTH_SHORT
    )

    @Deprecated("Will be removed with 0.13.0.")
    fun clearBackStack()

    fun hideKeyboard()

    fun onBackPressed()
}
