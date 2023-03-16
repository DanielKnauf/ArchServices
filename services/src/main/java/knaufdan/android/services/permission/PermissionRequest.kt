package knaufdan.android.services.permission

import androidx.annotation.StringRes
import knaufdan.android.services.R

data class PermissionRequest(
    val permission: String,
    @StringRes val rationale: Int = R.string.arch_services_permission_request_resolver_rationale
)
