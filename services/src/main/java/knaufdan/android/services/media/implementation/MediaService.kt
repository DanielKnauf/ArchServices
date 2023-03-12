package knaufdan.android.services.media.implementation

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import knaufdan.android.core.common.extensions.toBitmap
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.services.R
import knaufdan.android.services.media.EditImageConfig
import knaufdan.android.services.media.IMediaRequestResolver
import knaufdan.android.services.media.IMediaService
import knaufdan.android.services.media.IMediaServiceConfig
import knaufdan.android.services.media.IPictureResult
import knaufdan.android.services.media.IPictureResult.Companion.pictureError
import knaufdan.android.services.permission.IPermissionRequestResolver
import knaufdan.android.services.permission.PermissionRequest
import knaufdan.android.services.permission.PermissionResult
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

internal class MediaService(
    private val contextProvider: IContextProvider
) : IMediaService {

    private val context: Context
        get() = contextProvider.getContext()
    private val mediaRequestResolver: IMediaRequestResolver?
        get() = context as? IMediaRequestResolver
    private val permissionRequestResolver: IPermissionRequestResolver?
        get() = context as? IPermissionRequestResolver
    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private val cameraPermissionRequest: PermissionRequest by lazy {
        PermissionRequest(
            permission = Manifest.permission.CAMERA,
            rationale = R.string.arch_service_media_service_camera_rationale
        )
    }
    private val writeExternalStoragePermissionRequest: PermissionRequest by lazy {
        PermissionRequest(
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE,
            rationale = R.string.arch_service_media_service_camera_rationale
        )
    }

    override fun configure(adjust: IMediaServiceConfig.() -> Unit) = adjust(config)

    override fun takePicture(onPictureTaken: (IPictureResult) -> Unit) {
        val hasInvalidConfig = config.validate().not()
        if (hasInvalidConfig) {
            return onPictureTaken(pictureError("Invalid MediaServiceConfig (config=$config)."))
        }

        val mediaRequestResolver = mediaRequestResolver
            ?: return onPictureTaken(IPictureResult.Error(MediaRequestResolverException))

        val permissionRequestResolver = permissionRequestResolver
            ?: return onPictureTaken(IPictureResult.Error(PermissionRequestResolverException))

        permissionRequestResolver.requestPermission(cameraPermissionRequest) { result ->
            when (result) {
                PermissionResult.DENIED -> onPictureTaken(pictureError("Camera permission denied."))
                PermissionResult.GRANTED -> {
                    val uri =
                        FileProvider.getUriForFile(
                            context.applicationContext,
                            config.authority,
                            context.createCacheImage()
                        )

                    mediaRequestResolver.takePicture(
                        uri = uri,
                        onResult = onPictureTaken
                    )
                }
            }
        }
    }

    override fun editPicture(
        uri: Uri,
        onPictureEdited: (IPictureResult) -> Unit
    ) {
        val hasInvalidConfig = config.validate().not()
        if (hasInvalidConfig) {
            return onPictureEdited(pictureError("Invalid MediaServiceConfig (config=$config)."))
        }

        val targetUri =
            runCatching {
                FileProvider.getUriForFile(
                    context.applicationContext,
                    config.authority,
                    context.createFileImage()
                )
            }.getOrElse { e ->
                return onPictureEdited(
                    pictureError("Error while getting picture to edit (error=$e).")
                )
            }

        editPicture(
            editConfig = EditImageConfig(
                sourceUri = uri,
                targetUri = targetUri
            ),
            onPictureEdited = onPictureEdited
        )
    }

    override fun editPicture(
        editConfig: EditImageConfig,
        onPictureEdited: (IPictureResult) -> Unit
    ) = mediaRequestResolver
        ?.editPicture(
            config = editConfig,
            onResult = onPictureEdited
        )
        ?: onPictureEdited(IPictureResult.Error(MediaRequestResolverException))

    override fun savePicture(
        uri: Uri,
        displayName: String,
        directoryName: String,
        onPictureSaved: (IPictureResult) -> Unit
    ) {
        val bitmap = uri.toBitmap(contentResolver) ?: return onPictureSaved(
            pictureError("Could not convert uri to bitmap (uri=$uri).")
        )

        if (Build.VERSION.SDK_INT >= 29) {
            savePictureToGallery(
                bitmap = bitmap,
                displayName = displayName,
                directoryName = directoryName,
                onPictureSaved = onPictureSaved
            )
        } else {
            permissionRequestResolver?.requestPermission(
                writeExternalStoragePermissionRequest
            ) { result ->
                when (result) {
                    PermissionResult.DENIED ->
                        onPictureSaved(pictureError("Permission [Write External Storage] denied."))
                    PermissionResult.GRANTED -> {
                        val directory = File(directoryName.toDirectoryPath())
                        findOrCreateFile(directory)

                        val file = File(directory, defaultFileName)
                        saveImageToStream(
                            bitmap,
                            FileOutputStream(file)
                        )

                        val values = contentValues(displayName)
                        values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                        onPictureSaved(IPictureResult.Success(uri))
                    }
                }
            } ?: run { onPictureSaved(IPictureResult.Error(PermissionRequestResolverException)) }
        }
    }

    override fun selectPicture(
        onPictureSelected: (IPictureResult) -> Unit
    ) = mediaRequestResolver
        ?.selectPicture(onPictureSelected)
        ?: onPictureSelected(IPictureResult.Error(MediaRequestResolverException))

    override fun deleteFile(uri: Uri) {
        runCatching {
            contentResolver.delete(uri, null, null)
        }.getOrElse { e ->
            Log.e("MediaService", "Failed to not delete file at $uri (msg=${e.message}).")
        }
    }

    @RequiresApi(29)
    private fun savePictureToGallery(
        bitmap: Bitmap,
        displayName: String,
        directoryName: String,
        onPictureSaved: (IPictureResult) -> Unit
    ) {
        var tempUri: Uri? = null

        runCatching {
            val values = contentValues(displayName) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName.toDirectoryPath())
            }

            val uri = contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                ?: throw IOException("Failed to create new MediaStore record.")

            tempUri = uri

            val outputStream = contentResolver
                .openOutputStream(uri)
                ?: throw IOException("Could not open output stream.")

            saveImageToStream(bitmap, outputStream)
            onPictureSaved(IPictureResult.Success(uri))
        }.getOrElse { error ->
            tempUri?.let { orphanUri ->
                contentResolver.delete(orphanUri, null, null)
            }

            onPictureSaved(IPictureResult.Error(error))
        }
    }

    private fun saveImageToStream(
        bitmap: Bitmap,
        outputStream: OutputStream
    ) = outputStream.use { stream ->
        val isCompressed = bitmap.compress(
            EditImageConfig.DEFAULT_COMPRESSION_FORMAT,
            EditImageConfig.DEFAULT_COMPRESSION_QUALITY,
            stream
        )

        if (isCompressed.not()) throw IOException("Failed to save bitmap.")
    }

    companion object {

        private val config: MediaServiceConfig = MediaServiceConfig.EMPTY

        private fun String.toDirectoryPath(): String = getPictureDirectory() + separator + this

        private fun getPictureDirectory(): String =
            when (Build.VERSION.SDK_INT >= 29) {
                true -> Environment.DIRECTORY_PICTURES
                false -> Environment.getExternalStorageDirectory().toString()
            }

        private fun contentValues(
            displayName: String,
            block: ContentValues.() -> Unit = {}
        ): ContentValues =
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)

                if (Build.VERSION.SDK_INT >= 29) {
                    put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                }

                block()
            }
    }
}
