package knaufdan.android.services.permission

enum class PermissionResult {
    GRANTED,
    DENIED;

    companion object {

        val PermissionResult.isGranted: Boolean
            get() = this == GRANTED

        fun Boolean.toPermissionResult(): PermissionResult =
            when (this) {
                true -> GRANTED
                false -> DENIED
            }
    }
}
