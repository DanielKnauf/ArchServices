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
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogStyle
import kotlin.reflect.KClass

interface INavigationService {

    var containerViewId: ContainerViewId

    val fragmentManager: FragmentManager?

    val navigationController: NavController?

    fun toDestination(directions: NavDirections)

    fun toFragment(
        transaction: IFragmentTransaction,
        containerViewId: ContainerViewId = this.containerViewId
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

    /**
     * Iterates over all fragments of the current activity and returns first instance of [fragmentClass].
     *
     * @param fragmentClass which instance should be returned.
     */
    fun <T : Fragment> getFragment(fragmentClass: KClass<out T>): T?

    fun showDialog(
        component: IComponent<IComponentViewModel>,
        fragmentTag: FragmentTag = "",
        dialogStyle: DialogStyle = DialogStyle.DEFAULT
    )

    fun showDialog(
        dialog: DialogFragment,
        fragmentTag: FragmentTag = ""
    )

    fun showToast(
        @StringRes text: Int,
        duration: Int = Toast.LENGTH_SHORT
    )

    fun hideKeyboard()

    fun popFragmentBackStack()
}
