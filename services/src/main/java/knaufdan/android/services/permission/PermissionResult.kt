package knaufdan.android.services.permission

enum class PermissionResult {
    GRANTED,
    DENIED;

    companion object {

        val PermissionResult.isGranted: Boolean
            get() = this == GRANTED

        fun Boolean.toPermissionResult(): PermissionResult = if (this) GRANTED else DENIED
    }
}
