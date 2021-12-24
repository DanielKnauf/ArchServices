package knaufdan.android.arch.databinding.view

import android.graphics.Typeface

enum class TextStyle {
    NORMAL,
    BOLD,
    ITALIC,
    BOLD_ITALIC;

    fun toTypeFace(): Int =
        when (this) {
            NORMAL -> Typeface.NORMAL
            BOLD -> Typeface.BOLD
            ITALIC -> Typeface.ITALIC
            BOLD_ITALIC -> Typeface.BOLD_ITALIC
        }
}
