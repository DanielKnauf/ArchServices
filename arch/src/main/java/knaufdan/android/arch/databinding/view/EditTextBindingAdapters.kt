package knaufdan.android.arch.databinding.view

import android.widget.EditText
import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter

@BindingAdapter(
    value = [
        "focusText",
        "focusTextDelay"
    ],
    requireAll = false
)
fun EditText.bindFocus(
    focus: Boolean,
    delay: Number?
) {
    if (focus.not()) return clearFocus()

    val select = {
        requestFocus()
        setSelection(text.length)
    }

    if (delay == null) {
        select()
        return
    }

    postDelayed(delay.toLong()) { select() }
}
