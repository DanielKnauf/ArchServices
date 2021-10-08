package knaufdan.android.arch.databinding.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import knaufdan.android.core.resources.IResourceProvider

@BindingAdapter("src")
fun ImageView.bindSrc(bitmap: Bitmap?) {
    bitmap ?: return

    setImageBitmap(bitmap)
}

@BindingAdapter("src")
fun ImageView.bindSrc(@DrawableRes imageRes: Int) {
    if (imageRes == IResourceProvider.INVALID_RES_ID) return

    setImageResource(imageRes)
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
fun ImageView.bindImage(
    imageUrl: String?,
    loadingImage: Drawable?,
    @DrawableRes loadingImageRes: Int?,
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE,
    onlyScaleDown: Boolean = false
) {
    if (imageUrl == null || imageUrl.isBlank()) {
        return
    }

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
