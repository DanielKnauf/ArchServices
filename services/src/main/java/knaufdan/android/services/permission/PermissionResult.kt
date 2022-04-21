package knaufdan.android.services.permission

enum class PermissionResult {
    GRANTED,
    DENIED;

    companion object {

        fun Boolean.toPermissionResult(): PermissionResult = if (this) GRANTED else DENIED
    }
}
