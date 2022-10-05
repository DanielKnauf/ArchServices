package knaufdan.android.arch.databinding.view

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Scale
import knaufdan.android.core.resources.IResourceProvider
import java.util.WeakHashMap

private val images: WeakHashMap<ImageView, Uri> by lazy(::WeakHashMap)

@BindingAdapter("src")
fun ImageView.bindSrc(source: Any?) {
    source ?: return

    when (source) {
        is Int -> resource(source)
        is String -> uri(source.toUri())
    }
}

@BindingAdapter("android:src")
fun ImageView.bitmap(bitmap: Bitmap?) {
    bitmap ?: return

    setImageBitmap(bitmap)
}

@BindingAdapter("android:src")
fun ImageView.resource(@DrawableRes imageRes: Int) {
    if (imageRes == IResourceProvider.INVALID_RES_ID) return

    setImageResource(imageRes)
}

// TODO: rename
@BindingAdapter("android:src")
fun ImageView.uri(imageUri: Uri?) {
    if (imageUri == null || imageUri == Uri.EMPTY) return

    bindImageUri(imageUri)
}

@BindingAdapter("tint")
fun ImageView.iconTint(@ColorInt color: Int?) {
    color ?: return

    imageTintList = ColorStateList.valueOf(color)
}

@BindingAdapter(
    value = [
        "imageUri",
        "loadingImage",
        "loadingImageRes",
        "resizeWidth",
        "resizeHeight",
        "scaleType"
    ],
    requireAll = false
)
fun ImageView.bindImageUri(
    imageUri: Uri?,
    loadingImage: Drawable? = null,
    @DrawableRes loadingImageRes: Int? = null,
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE
) {
    if (imageUri == null || imageUri == Uri.EMPTY) return

    val uri = images[this]
    if (uri == imageUri) return

    images[this] = imageUri

    context
        .imageLoader
        .enqueue(
            ImageRequest
                .Builder(context)
                .data(imageUri)
                .target(this)
                .setLoadingImage(
                    loadingImage = loadingImage,
                    loadingImageRes = loadingImageRes
                )
                .applyTransformation(
                    resizeWidth = resizeWidth,
                    resizeHeight = resizeHeight,
                    scaleType = scaleType
                )
                .build()
        )
}

@BindingAdapter(
    value = [
        "imageUrl",
        "loadingImage",
        "loadingImageRes",
        "resizeWidth",
        "resizeHeight",
        "scaleType"
    ],
    requireAll = false
)
fun ImageView.bindImageUrl(
    imageUrl: String?,
    loadingImage: Drawable?,
    @DrawableRes loadingImageRes: Int?,
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE
) {
    if (imageUrl == null || imageUrl.isBlank()) return

    context
        .imageLoader
        .enqueue(
            ImageRequest
                .Builder(context)
                .data(imageUrl)
                .target(this)
                .setLoadingImage(
                    loadingImage = loadingImage,
                    loadingImageRes = loadingImageRes
                )
                .applyTransformation(
                    resizeWidth = resizeWidth,
                    resizeHeight = resizeHeight,
                    scaleType = scaleType
                )
                .build()
        )
}

private fun ImageRequest.Builder.setLoadingImage(
    loadingImage: Drawable?,
    loadingImageRes: Int?
) =
    when {
        loadingImage != null -> placeholder(loadingImage)
        loadingImageRes != null -> placeholder(loadingImageRes)
        else -> this
    }

private fun ImageRequest.Builder.applyTransformation(
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE
) =
    apply {
        if (resizeWidth > 0 || resizeHeight > 0) {
            size(resizeWidth, resizeHeight)

            when (scaleType) {
                ScaleType.CENTER_INSIDE -> scale(Scale.FIT)
                ScaleType.CENTER_CROP -> scale(Scale.FILL)
                null -> Unit
            }
        }
    }

enum class ScaleType {
    CENTER_INSIDE,
    CENTER_CROP
}
