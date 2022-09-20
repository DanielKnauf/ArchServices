package knaufdan.android.services.media

import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import knaufdan.android.core.resources.IResourceProvider
import knaufdan.android.services.R
import knaufdan.android.services.media.typealia.X
import knaufdan.android.services.media.typealia.Y

data class EditImageConfig(
    val sourceUri: Uri,
    val targetUri: Uri,
    val aspectRatio: Pair<X, Y> = 1f to 1f,
    val maxResultSize: Size = Size(4000, 4000),
    @StringRes val toolbarTitle: Int = IResourceProvider.INVALID_RES_ID,
    @ColorRes val toolbarColor: Int = R.color.media_resolver_edit_picture_contract_toolbar,
    @ColorRes val toolbarControlsColor: Int = R.color.media_resolver_edit_picture_contract_toolbar_controls,
    @ColorRes val statusBarColor: Int = R.color.media_resolver_edit_picture_contract_statusbar,
    @ColorRes val backgroundColor: Int = R.color.media_resolver_edit_picture_contract_background,
    @ColorRes val activeControlColor: Int = R.color.media_resolver_edit_picture_contract_active_control,
    val isCropGridVisible: Boolean = false,
    val compressionFormat: Bitmap.CompressFormat = DEFAULT_COMPRESSION_FORMAT,
    val compressionQuality: Int = DEFAULT_COMPRESSION_QUALITY
) {

    companion object {

        const val DEFAULT_COMPRESSION_QUALITY: Int = 92
        val DEFAULT_COMPRESSION_FORMAT: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
    }
}
