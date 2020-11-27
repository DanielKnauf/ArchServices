package knaufdan.android.arch.mvvm

interface IBaseFragment<ViewModel : IAndroidBaseViewModel> : IAndroidComponent<ViewModel> {
    /**
     * NOTE: As default the simple className is used.
     *
     * @return [FragmentTag] which is used to identify the fragment in the [FragmentStack].
     */
    fun getFragmentTag(): FragmentTag = this::class.java.simpleName
}
