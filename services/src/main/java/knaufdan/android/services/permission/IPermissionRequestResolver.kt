package knaufdan.android.services.permission

import androidx.activity.ComponentActivity

interface IPermissionRequestResolver {

    /**
     * Initializes the [IPermissionRequestResolver] by registering a permission request at the
     * [activity].
     *
     * NOTE: must be called before any permission can be requested with [requestPermission].
     *
     * @param activity at which the permission request is registered.
     */
    fun registerPermissionRequestsFor(activity: ComponentActivity)

    /**
     * Requests the permission defined in the [config]. Automatically show a dialog displaying the
     * rational defined in the [config] if the Android system indicates this.
     *
     * Calls [onResult] with [PermissionResult.GRANTED] if permission is already given or user gives
     * permission. Otherwise, [onResult] is called with [PermissionResult.DENIED].
     *
     * NOTE: returns [PermissionResult.DENIED] if no [ComponentActivity] is set via
     * [registerPermissionRequestsFor].
     *
     * @param config containing the requested permission and displayed rational.
     * @param onResult callback to be called when a [PermissionResult] is available.
     */
    fun requestPermission(
        config: PermissionRequestConfig,
        onResult: (PermissionResult) -> Unit
    )
}
