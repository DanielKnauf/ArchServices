package knaufdan.android.services.media

import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.ucrop.UCrop

/**
 * Can be implemented by delegation: IMediaRequestResolver by MediaRequestResolver()
 *
 * [registerMediaContractsFor] should be called in [AppCompatActivity.onCreate].
 */
interface IMediaRequestResolver {

    /**
     * Initializes the [IMediaRequestResolver] by registering [ActivityResultContracts] for taking,
     * selecting and editing pictures at the [activity].
     *
     * NOTE: must be called before any action of the [IMediaRequestResolver] can be used inside the
     * scope of the [activity].
     *
     * @param activity at which the [ActivityResultContracts] are registered.
     */
    fun registerMediaContractsFor(activity: AppCompatActivity)

    /**
     * Opens the camera app to take a new picture.
     *
     * NOTE:
     * - does not request camera permission and will fail if not already granted.
     *
     * @param uri [Uri] to which the taken picture should be saved
     * @param onResult callback to process taken picture in case of [IPictureResult.Success]
     * or handle [IPictureResult.Error]
     */
    fun takePicture(
        uri: Uri,
        onResult: (IPictureResult) -> Unit
    )

    /**
     * Opens the gallery app to select a new picture.
     *
     * NOTE:
     * - does not request any permission (e.g. [Manifest.permission.READ_EXTERNAL_STORAGE] and will
     * fail if needed and not granted.
     *
     * @param onResult callback to process selected picture in case of [IPictureResult.Success]
     * or handle [IPictureResult.Error]
     */
    fun selectPicture(onResult: (IPictureResult) -> Unit)

    /**
     * Opens [UCrop] to edit file specified by the [config] (e.g. rotating or cropping).
     *
     * @param config [EditImageConfig] to configure and adjust the [UCrop] activity
     * @param onResult callback to process the edited picture in case of [IPictureResult.Success]
     * or handle [IPictureResult.Error]
     */
    fun editPicture(
        config: EditImageConfig,
        onResult: (IPictureResult) -> Unit
    )
}
