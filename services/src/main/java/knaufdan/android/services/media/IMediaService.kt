package knaufdan.android.services.media

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.yalantis.ucrop.UCrop
import knaufdan.android.services.permission.IPermissionRequestResolver
import java.util.Date

/**
 * NOTE: the [Context] in which the [IMediaService] is running must implement
 * [IPermissionRequestResolver] and [IMediaRequestResolver] to
 * resolve requests regarding permissions or perform actions (e.g. take a picture).
 */
interface IMediaService {

    /**
     * NOTE: a authority must be set via [configure] before [takePicture] or [editPicture]
     * can be used.
     *
     * @param adjust extension function block to adjust the used [IMediaServiceConfig]
     */
    fun configure(adjust: IMediaServiceConfig.() -> Unit)

    /**
     * Requests camera permission if not already granted and opens the camera app to take a new
     * picture.
     *
     * NOTE:
     * - a authority must be set via [configure] before [takePicture] or [editPicture]
     * can be used.
     * - [IPermissionRequestResolver] and [IMediaRequestResolver] must be implemented by [Context].
     * Otherwise, the method will always return [IPictureResult.Error].
     *
     * @param onPictureTaken callback to process taken picture in case of [IPictureResult.Success]
     * or handle [IPictureResult.Error]
     */
    fun takePicture(onPictureTaken: (IPictureResult) -> Unit)

    /**
     * Opens [UCrop] to edit picture defined by the [uri] (e.g. rotating or cropping).
     *
     * NOTE:
     * - a authority must be set via [configure] before [takePicture] or [editPicture]
     * can be used.
     * - [IMediaRequestResolver] must be implemented by context. Otherwise, the method will always
     * return [IPictureResult.Error].
     *
     * @param uri [Uri] of the picture which should be edited
     * @param onPictureEdited callback to process the edited picture in case of
     * [IPictureResult.Success] or handle [IPictureResult.Error]
     */
    fun editPicture(
        uri: Uri,
        onPictureEdited: (IPictureResult) -> Unit
    )

    /**
     * Opens [UCrop] to edit file specified by the [EditImageConfig.sourceUri] (e.g. rotating or
     * cropping).
     *
     * NOTE:
     * - [IMediaRequestResolver] must be implemented by context. Otherwise, the method will always
     * return [IPictureResult.Error].
     *
     * @param editConfig config to define source and target uris and adjust UCrop editing
     * @param onPictureEdited callback to process the edited picture in case of
     * [IPictureResult.Success] or handle [IPictureResult.Error]
     */
    fun editPicture(
        editConfig: EditImageConfig,
        onPictureEdited: (IPictureResult) -> Unit
    )

    /**
     * Opens the gallery app to select a single picture.
     *
     * NOTE:
     * - [IMediaRequestResolver] must be implemented by context. Otherwise, the method will always
     * return [IPictureResult.Error].
     *
     * @param onPictureSelected callback to process the edited picture in case of
     * [IPictureResult.Success] or handle [IPictureResult.Error]
     */
    fun selectPicture(onPictureSelected: (IPictureResult) -> Unit)

    /**
     * Saves the picture defined by the given [uri] to the [directoryName]
     * under the defined [directoryName].
     *
     * NOTE:
     * - [IPermissionRequestResolver] must be implemented by [Context] if minSdk is lower 29,
     * because [Manifest.permission.READ_EXTERNAL_STORAGE] must be requested. Otherwise, the method
     * will always return [IPictureResult.Error].
     *
     * @param uri [Uri] of the picture which should be saved
     * @param displayName name used for the picture file entry
     * @param directoryName directory to which the picture is saved
     * @param onPictureSaved callback to process the edited picture in case of
     * [IPictureResult.Success] or handle [IPictureResult.Error]
     */
    fun savePicture(
        uri: Uri,
        displayName: String = defaultDisplayName,
        directoryName: String = defaultDirectoryName,
        onPictureSaved: (IPictureResult) -> Unit = {}
    )

    /**
     * Deletes all rows specified by the [uri].
     *
     * @param uri [Uri] of the row to delete
     */
    fun deleteFile(uri: Uri)

    companion object {

        private val defaultDisplayName
            get() = "picture_${Date().time}"

        private val defaultDirectoryName by lazy(Environment::DIRECTORY_DCIM)
    }
}
