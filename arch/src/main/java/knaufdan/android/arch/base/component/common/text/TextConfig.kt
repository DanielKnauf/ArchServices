package knaufdan.android.arch.base.component.common.text

import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.arch.R
import knaufdan.android.arch.databinding.view.TextGravity

data class TextConfig(
    val text: LiveData<Spannable>,
    @DimenRes val textSize: Int = R.dimen.default_text_size,
    @ColorRes val textColor: Int = R.color.default_text_color,
    val textGravity: TextGravity = TextGravity.DEFAULT,
    @DimenRes val marginTop: Int = R.dimen.default_margin_horizontal,
    @DimenRes val marginBottom: Int = R.dimen.default_margin_horizontal,
    @DimenRes val marginLeft: Int = R.dimen.default_margin_vertical,
    @DimenRes val marginRight: Int = R.dimen.default_margin_vertical,
    val onTextClicked: (text: String) -> Unit = {}
) {
    constructor(
        text: Spannable,
        @DimenRes textSize: Int = R.dimen.default_text_size,
        @ColorRes textColor: Int = R.color.default_text_color,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes marginTop: Int = R.dimen.default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.default_margin_vertical,
        onTextClicked: (text: String) -> Unit = {}
    ) : this(
        text = MutableLiveData(text),
        textSize = textSize,
        textColor = textColor,
        textGravity = textGravity,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        onTextClicked = onTextClicked
    )

    constructor(
        text: String,
        @DimenRes textSize: Int = R.dimen.default_text_size,
        @ColorRes textColor: Int = R.color.default_text_color,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes marginTop: Int = R.dimen.default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.default_margin_vertical,
        onTextClicked: (text: String) -> Unit = {}
    ) : this(
        text = MutableLiveData(SpannableString(text) as Spannable),
        textSize = textSize,
        textColor = textColor,
        textGravity = textGravity,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        onTextClicked = onTextClicked
    )
}
