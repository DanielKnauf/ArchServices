package knaufdan.android.services.media

import android.net.Uri

sealed interface IPictureResult {

    data class Success(val uri: Uri) : IPictureResult

    data class Error(val error: Throwable) : IPictureResult

    companion object {

        fun IPictureResult.doOnSuccess(block: (Uri) -> Unit) =
            when (this) {
                is Error -> Unit
                is Success -> block(uri)
            }

        fun pictureError(message: String): Error = Error(Exception(message))
    }
}
