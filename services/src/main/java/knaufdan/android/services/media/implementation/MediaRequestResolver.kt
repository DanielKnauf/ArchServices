@file:Suppress("unused")

package knaufdan.android.services.media.implementation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.ucrop.UCrop
import knaufdan.android.core.resources.IResourceProvider
import knaufdan.android.services.common.intent
import knaufdan.android.services.media.EditImageConfig
import knaufdan.android.services.media.IMediaRequestResolver
import knaufdan.android.services.media.IPictureResult
import knaufdan.android.services.media.IPictureResult.Companion.pictureError

class MediaRequestResolver : IMediaRequestResolver {

    private lateinit var takePictureContract: ActivityResultLauncher<Uri>
    private lateinit var selectPictureContract: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var editPictureContract: ActivityResultLauncher<EditImageConfig>

    private lateinit var lastTakePictureRequest: (IPictureResult) -> Unit
    private lateinit var lastSelectPictureRequest: (IPictureResult) -> Unit
    private lateinit var lastEditPictureRequest: (IPictureResult) -> Unit

    private lateinit var lastTakePictureUri: Uri

    private val imageOnlyMediaRequest: PickVisualMediaRequest by lazy {
        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
    }

    override fun registerMediaContractsFor(activity: AppCompatActivity) {
        takePictureContract =
            activity.registerForActivityResult(
                ActivityResultContracts.TakePicture()
            ) { isSuccessful ->
                lastTakePictureRequest(isSuccessful.toPictureResult(lastTakePictureUri))
            }

        selectPictureContract =
            activity.registerForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { pictureUri -> lastSelectPictureRequest(pictureUri.toPictureResult()) }

        editPictureContract =
            activity.registerForActivityResult(
                EditPictureResultContract
            ) { pictureUri -> lastEditPictureRequest(pictureUri.toPictureResult()) }
    }

    override fun takePicture(
        uri: Uri,
        onResult: (IPictureResult) -> Unit
    ) {
        lastTakePictureUri = uri
        lastTakePictureRequest = onResult
        takePictureContract.launch(uri)
    }

    override fun selectPicture(onResult: (IPictureResult) -> Unit) {
        lastSelectPictureRequest = onResult
        selectPictureContract.launch(imageOnlyMediaRequest)
    }

    override fun editPicture(
        config: EditImageConfig,
        onResult: (IPictureResult) -> Unit
    ) {
        lastEditPictureRequest = onResult
        editPictureContract.launch(config)
    }

    companion object {

        private object SelectPictureResultContract : ActivityResultContract<Unit, Uri>() {

            override fun createIntent(
                context: Context,
                input: Unit
            ): Intent = intentToSelect()

            override fun parseResult(
                resultCode: Int,
                intent: Intent?
            ): Uri =
                when (resultCode) {
                    Activity.RESULT_OK -> intent?.data.orEmpty()
                    else -> Uri.EMPTY
                }

            private fun intentToSelect(): Intent =
                intent(intentAction) {
                    type = MIME_TYPE_IMAGE
                }

            private val intentAction: String
                get() = when (Build.VERSION.SDK_INT <= 29) {
                    true -> Intent.ACTION_OPEN_DOCUMENT
                    false -> Intent.ACTION_PICK
                }

            private const val MIME_TYPE_IMAGE = "image/*"
        }

        private object EditPictureResultContract : ActivityResultContract<EditImageConfig, Uri>() {

            override fun createIntent(
                context: Context,
                input: EditImageConfig
            ): Intent = context.intentToCrop(input)

            override fun parseResult(
                resultCode: Int,
                intent: Intent?
            ): Uri =
                when (resultCode) {
                    Activity.RESULT_OK -> intent?.let(UCrop::getOutput).orEmpty()
                    UCrop.RESULT_ERROR ->
                        intent
                            ?.let(UCrop::getError)
                            ?.run {
                                Log.e(
                                    this::class.java.simpleName,
                                    "Error editing image. Message = $message"
                                )
                            }
                            ?.run { Uri.EMPTY }
                            .orEmpty()
                    else -> Uri.EMPTY
                }

            private fun Context.intentToCrop(config: EditImageConfig): Intent =
                UCrop
                    .of(config.sourceUri, config.targetUri)
                    .withAspectRatio(config.aspectRatio.first, config.aspectRatio.second)
                    .withMaxResultSize(config.maxResultSize.width, config.maxResultSize.height)
                    .withOptions(
                        cropOptions {
                            setCompressionFormat(config.compressionFormat)
                            setCompressionQuality(config.compressionQuality)

                            setShowCropGrid(config.isCropGridVisible)

                            if (config.toolbarTitle != IResourceProvider.INVALID_RES_ID) {
                                setToolbarTitle(getString(config.toolbarTitle))
                            }

                            setToolbarColor(getColor(config.toolbarColor))
                            setToolbarWidgetColor(getColor(config.toolbarControlsColor))
                            setStatusBarColor(getColor(config.statusBarColor))
                            setActiveControlsWidgetColor(getColor(config.activeControlColor))
                            setRootViewBackgroundColor(getColor(config.backgroundColor))
                        }
                    )
                    .getIntent(this)
        }

        private fun Uri?.orEmpty(): Uri = this ?: Uri.EMPTY

        private fun cropOptions(block: UCrop.Options.() -> Unit): UCrop.Options =
            UCrop.Options().apply(block)

        private fun Boolean.toPictureResult(uri: Uri): IPictureResult =
            when (this) {
                true -> IPictureResult.Success(uri)
                false -> pictureError("Error while taking picture.")
            }

        private fun Uri?.toPictureResult(): IPictureResult =
            when (this) {
                null, Uri.EMPTY -> pictureError("No media selected.")
                else -> IPictureResult.Success(this)
            }
    }
}
