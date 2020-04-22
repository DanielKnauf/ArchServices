package knaufdan.android.arch.databinding.views

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

@BindingAdapter(
    value = [
        "imageUrl",
        "resizeWidth",
        "resizeHeight",
        "scaleType",
        "onlyScaleDown"
    ],
    requireAll = false
)
fun ImageView.bindImage(
    imageUrl: String,
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE,
    onlyScaleDown: Boolean = false
) {
    if (imageUrl.isBlank()) {
        return
    }

    Picasso.get()
        .load(imageUrl)
        .applyTransformation(resizeWidth, resizeHeight, scaleType, onlyScaleDown)
        .into(this)
}

private fun RequestCreator.applyTransformation(
    resizeWidth: Int = -1,
    resizeHeight: Int = -1,
    scaleType: ScaleType? = ScaleType.CENTER_INSIDE,
    onlyScaleDown: Boolean = false
) = this.apply {
    if (resizeWidth > 0 || resizeHeight > 0) {
        resize(resizeWidth, resizeHeight)

        when (scaleType) {
            ScaleType.CENTER_INSIDE -> centerInside()
            ScaleType.CENTER_CROP -> centerCrop()
        }

        if (onlyScaleDown) onlyScaleDown()
    }
}

enum class ScaleType {
    CENTER_INSIDE,
    CENTER_CROP
}
