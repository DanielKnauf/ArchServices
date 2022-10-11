package knaufdan.android.arch.navigation.implementation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import knaufdan.android.arch.R
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.ComponentDialogFragmentFactory
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogStyle
import knaufdan.android.arch.navigation.ContainerViewId
import knaufdan.android.arch.navigation.FragmentTag
import knaufdan.android.arch.navigation.IFragmentTransaction
import knaufdan.android.arch.navigation.INavigationService
import knaufdan.android.arch.navigation.IWebTarget
import knaufdan.android.arch.utils.getColorCompat
import knaufdan.android.core.common.applyIf
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.core.resources.IResourceProvider
import kotlin.reflect.KClass

internal class NavigationService(
    private val contextProvider: IContextProvider
) : INavigationService {

    override val fragmentManager: FragmentManager?
        get() = activity?.supportFragmentManager

    override var containerViewId: ContainerViewId = R.id.arch_fragment_container

    override val navigationController: NavController?
        get() = runCatching { fragmentManager?.navigationController }.getOrNull()

    private val context: Context
        get() = contextProvider.getContext()
    private val activity: AppCompatActivity?
        get() = context as? AppCompatActivity

    override fun toDestination(directions: NavDirections) {
        navigationController?.navigate(directions)
    }

    override fun toFragment(
        transaction: IFragmentTransaction,
        containerViewId: ContainerViewId
    ) {
        fragmentManager?.commit {
            when (val animation = transaction.animation) {
                null -> setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                else -> setCustomAnimations(
                    animation.enter,
                    animation.exit,
                    animation.popEnter,
                    animation.popExit
                )
            }

            setReorderingAllowed(true)
            if (transaction.addToBackStack) addToBackStack(transaction.fragment.tag)

            when (transaction) {
                is IFragmentTransaction.Add -> add(containerViewId, transaction.fragment)
                is IFragmentTransaction.Replace -> replace(containerViewId, transaction.fragment)
            }
        }
    }

    override fun toEmail(
        recipient: String,
        subject: String
    ) =
        withContext {
            val intent =
                intentForEmail(
                    recipient = recipient,
                    subject = subject
                )

            runCatching {
                startActivity(
                    Intent.createChooser(
                        intent,
                        getString(R.string.arch_navigation_service_mail_client_chooser)
                    )
                )
            }.getOrElse { error ->
                Log.e(
                    NavigationService::class.java.name,
                    "Could not resolve intent for e-mail" +
                        "\n- intent=$intent" +
                        "\n- error=$error"
                )
                showToast(R.string.arch_navigation_service_error_no_mail_client)
            }
        }

    override fun toWeb(target: IWebTarget) {
        when (target) {
            is IWebTarget.CustomTab ->
                runCatching {
                    CustomTabsIntent.Builder()
                        .applyIf(target.toolbarColor != IResourceProvider.INVALID_RES_ID) {
                            setDefaultColorSchemeParams(
                                CustomTabColorSchemeParams.Builder()
                                    .setToolbarColor(context.getColorCompat(target.toolbarColor))
                                    .build()
                            )
                        }
                        .build()
                        .launchUrl(context, Uri.parse(target.url))
                }.getOrElse {
                    showToast(R.string.arch_navigation_service_error_no_browser)
                }
            is IWebTarget.PlayStore ->
                runCatching {
                    context.startActivity(
                        intent(Intent.ACTION_VIEW) {
                            data = Uri.parse(target.url)
                            setPackage("com.android.vending")
                        }
                    )
                }.getOrElse {
                    showToast(R.string.arch_navigation_service_error_play_store)
                }
        }
    }

    override fun <T : Fragment> getFragment(fragmentClass: KClass<out T>): T? =
        fragmentManager
            ?.fragments
            ?.filterIsInstance(fragmentClass.java)
            ?.firstOrNull()

    override fun showDialog(
        component: IComponent<IComponentViewModel>,
        fragmentTag: FragmentTag,
        dialogStyle: DialogStyle
    ) {
        val fragmentManager = fragmentManager ?: return

        val fragment =
            ComponentDialogFragmentFactory(
                component = component,
                dialogStyle = dialogStyle
            ).run {
                fragmentManager.fragmentFactory = this
                instantiate()
            }

        val tag = fragmentTag.ifBlank { component.getId() }

        context.showDialog(
            fragment = fragment,
            fragmentTag = tag,
            dialogSize = dialogStyle.dialogSize
        )
    }

    override fun showDialog(
        dialog: DialogFragment,
        fragmentTag: FragmentTag
    ) {
        fragmentManager?.run {
            dialog
                .show(this, fragmentTag)
        }
    }

    override fun showToast(
        text: Int,
        duration: Int
    ) {
        Toast.makeText(context, text, duration).show()
    }

    override fun hideKeyboard() {
        withActivity {
            currentFocus?.run {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    override fun onBackPressed() {
        withActivity { onBackPressed() }
    }

    internal fun dismissDialogBySystem(
        fragmentTag: String
    ) =
        context.dismissDialog(
            fragmentTag = fragmentTag,
            result = null,
            dismissedBySystem = true
        )

    private fun <T> withActivity(block: AppCompatActivity.() -> T) = activity?.block()

    private fun <T> withContext(block: Context.() -> T) = context.block()

    companion object {

        private fun intent(action: String, block: Intent.() -> Unit): Intent =
            Intent(action).apply(block)

        private fun intentForEmail(
            recipient: String,
            subject: String
        ): Intent =
            intent(Intent.ACTION_SENDTO) {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }
    }
}
