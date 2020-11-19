package knaufdan.android.arch.base.component.viewpager.implementation

import androidx.viewpager2.widget.ViewPager2

enum class ViewPagerOrientation {
    VERTICAL,
    HORIZONTAL;

    companion object {
        fun ViewPagerOrientation?.toAndroidOrientation(): Int =
            when (this) {
                VERTICAL -> ViewPager2.ORIENTATION_VERTICAL
                HORIZONTAL -> ViewPager2.ORIENTATION_HORIZONTAL
                else -> ViewPager2.ORIENTATION_HORIZONTAL
            }
    }
}
