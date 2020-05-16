package knaufdan.android.arch.mvvm

typealias FragmentTag = String

interface IBaseFragment<ViewModel : IAndroidBaseViewModel> : IAndroidComponent<ViewModel> {
    fun setBackPressed(isBackPressed: Boolean) = Unit

    /**
     * @return [FragmentTag] which is used to identify the fragment in the [FragmentStack]. Per default the simple className is used.
     */
    fun getFragmentTag(): FragmentTag = this::class.java.simpleName
}
