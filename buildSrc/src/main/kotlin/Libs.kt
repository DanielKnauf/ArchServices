import Versions.vNavigation

object Libs {

    object AndroidX {
        private const val vConstraintLayout = "2.0.4"
        private const val vFragment = "1.3.4"
        private const val vPaging = "3.0.1"
        private const val vViewPager2 = "1.0.0"

        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$vConstraintLayout"
        const val fragment = "androidx.fragment:fragment-ktx:$vFragment"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$vNavigation"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$vNavigation"
        const val paging = "androidx.paging:paging-runtime:$vPaging"
        const val viewPager2 = "androidx.viewpager2:viewpager2:$vViewPager2"
    }

    private const val vLiveDataKit = "0.1.1"
    const val liveDataKit = "com.github.DanielKnauf:livedata-kit:$vLiveDataKit"

    private const val vUCrop = "2.2.6"
    const val uCrop = "com.github.yalantis:ucrop:$vUCrop"
}
