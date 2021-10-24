package knaufdan.android.arch.databinding.view

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider

@BindingAdapter("android:value")
fun Slider.bindValueSetter(newValue: Float) {
    if (value == newValue) return

    value = newValue
}

@InverseBindingAdapter(attribute = "android:value")
fun Slider.bindValueGetter(): Float = value

@BindingAdapter("android:valueAttrChanged")
fun Slider.bindValueChangedListener(attrChange: InverseBindingListener) {
    clearOnChangeListeners()

    addOnChangeListener { _, _, _ -> attrChange.onChange() }
}
