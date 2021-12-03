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
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import knaufdan.android.core.resources.IResourceProvider
import java.util.WeakHashMap

val images: WeakHashMap<ImageView, Uri> = WeakHashMap()

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
        "scaleType",
        "onlyScaleDown"
    ],
    requireAll = false
)
fun ImageView.bindImageUri(
    imageUri: Uri?,
    loadingImage: Drawable? = null,
    @DrawableRes loadingImageRes: Int? = null,
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE,
    onlyScaleDown: Boolean = false,
) {
    if (imageUri == null || imageUri == Uri.EMPTY) return

    val uri = images[this]
    if (uri == imageUri) return

    images[this] = imageUri

    Picasso.get()
        .load(imageUri)
        .applyTransformation(
            resizeWidth = resizeWidth,
            resizeHeight = resizeHeight,
            scaleType = scaleType,
            onlyScaleDown = onlyScaleDown
        )
        .setLoadingImage(
            loadingImage = loadingImage,
            loadingImageRes = loadingImageRes
        )
        .into(this)
}

@BindingAdapter(
    value = [
        "imageUrl",
        "loadingImage",
        "loadingImageRes",
        "resizeWidth",
        "resizeHeight",
        "scaleType",
        "onlyScaleDown"
    ],
    requireAll = false
)
fun ImageView.bindImageUrl(
    imageUrl: String?,
    loadingImage: Drawable?,
    @DrawableRes loadingImageRes: Int?,
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE,
    onlyScaleDown: Boolean = false,
) {
    if (imageUrl == null || imageUrl.isBlank()) return

    Picasso.get()
        .load(imageUrl)
        .applyTransformation(
            resizeWidth = resizeWidth,
            resizeHeight = resizeHeight,
            scaleType = scaleType,
            onlyScaleDown = onlyScaleDown
        )
        .setLoadingImage(
            loadingImage = loadingImage,
            loadingImageRes = loadingImageRes
        )
        .into(this)
}

private fun RequestCreator.setLoadingImage(
    loadingImage: Drawable?,
    loadingImageRes: Int?
) =
    when {
        loadingImage != null -> placeholder(loadingImage)
        loadingImageRes != null -> placeholder(loadingImageRes)
        else -> this
    }

private fun RequestCreator.applyTransformation(
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE,
    onlyScaleDown: Boolean = false
) =
    apply {
        if (resizeWidth > 0 || resizeHeight > 0) {
            resize(resizeWidth, resizeHeight)

            when (scaleType) {
                ScaleType.CENTER_INSIDE -> centerInside()
                ScaleType.CENTER_CROP -> centerCrop()
            }

            if (onlyScaleDown) {
                onlyScaleDown()
            }
        }
    }

enum class ScaleType {
    CENTER_INSIDE,
    CENTER_CROP
}
