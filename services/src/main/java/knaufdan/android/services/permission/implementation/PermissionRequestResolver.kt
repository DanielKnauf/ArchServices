package knaufdan.android.services.permission.implementation

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import knaufdan.android.services.R
import knaufdan.android.services.permission.IPermissionRequestResolver
import knaufdan.android.services.permission.PermissionRequestConfig
import knaufdan.android.services.permission.PermissionResult
import knaufdan.android.services.permission.PermissionResult.Companion.toPermissionResult
import java.lang.ref.WeakReference

class PermissionRequestResolver : IPermissionRequestResolver {

    private lateinit var requestPermissionContract: ActivityResultLauncher<String>
    private lateinit var lastPermissionRequest: (PermissionResult) -> Unit
    private lateinit var activity: WeakReference<ComponentActivity>
    private val permissionRequest = ActivityResultContracts.RequestPermission()

    override fun registerPermissionRequestsFor(activity: ComponentActivity) {
        this.activity = WeakReference(activity)

        requestPermissionContract =
            activity.registerForActivityResult(permissionRequest) { granted ->
                lastPermissionRequest.invoke(granted.toPermissionResult())
            }
    }

    override fun requestPermission(
        config: PermissionRequestConfig,
        onResult: (PermissionResult) -> Unit
    ) {
        val activity = activity.get() ?: return onResult(PermissionResult.DENIED)

        when {
            activity.hasPermission(config.permission) -> onResult(PermissionResult.GRANTED)
            activity.shouldShowRequestPermissionRationale(config.permission) ->
                activity.showRationale(config, onResult)
            else ->
                requestPermission(
                    permission = config.permission,
                    onResult = onResult
                )
        }
    }

    private fun ComponentActivity.showRationale(
        config: PermissionRequestConfig,
        onResult: (PermissionResult) -> Unit
    ) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.arch_services_permission_request_resolver_rationale_title))
            .setMessage(config.rationale)
            .setCancelable(true)
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                requestPermission(config.permission, onResult)
            }
            .show()
    }

    private fun requestPermission(
        permission: String,
        onResult: (PermissionResult) -> Unit
    ) {
        lastPermissionRequest = onResult
        requestPermissionContract.launch(permission)
    }

    private fun Activity.hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
