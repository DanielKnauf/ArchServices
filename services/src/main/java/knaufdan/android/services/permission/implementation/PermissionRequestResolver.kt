package knaufdan.android.services.permission.implementation

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import knaufdan.android.core.common.extensions.hasPermission
import knaufdan.android.services.R
import knaufdan.android.services.permission.IPermissionRequestResolver
import knaufdan.android.services.permission.PermissionRequest
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
        request: PermissionRequest,
        onResult: (PermissionResult) -> Unit
    ) {
        val activity = activity.get() ?: return onResult(PermissionResult.DENIED)

        when {
            activity.hasPermission(request.permission) -> onResult(PermissionResult.GRANTED)
            activity.shouldShowRequestPermissionRationale(request.permission) ->
                activity.showRationale(request, onResult)
            else ->
                requestPermission(
                    permission = request.permission,
                    onResult = onResult
                )
        }
    }

    private fun ComponentActivity.showRationale(
        request: PermissionRequest,
        onResult: (PermissionResult) -> Unit
    ) =
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.arch_services_permission_request_resolver_rationale_title))
            .setMessage(request.rationale)
            .setCancelable(true)
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                requestPermission(request.permission, onResult)
            }
            .show()

    private fun requestPermission(
        permission: String,
        onResult: (PermissionResult) -> Unit
    ) {
        lastPermissionRequest = onResult
        requestPermissionContract.launch(permission)
    }
}
