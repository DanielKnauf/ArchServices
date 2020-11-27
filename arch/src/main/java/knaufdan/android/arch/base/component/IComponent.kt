package knaufdan.android.arch.base.component

import knaufdan.android.arch.base.BindingKey
import knaufdan.android.arch.base.LayoutRes

/**
 * A component defines a user interface element.
 *
 * Components can be added to different view group types, e.g. recycler view
 * (via RecyclerViewBindingAdapter) or fragments (via ComponentFragment).
 * By utilizing data binding within the layout this allows to nest different
 * components inside each other and build complex view structures. In addition
 * it enables a simple and easy way to replace single or multiple ui elements.
 *
 * On runtime the [DataSource] is set into the [LayoutRes] using the [BindingKey].
 * Therefore, the [LayoutRes] must contain <layout> as its outermost tag and the
 * [DataSource] must be placed as a <variable> with [BindingKey] as its name.
 *
 * When used with BaseActivity, BaseFragment or ComponentFragment a fragment manager
 * can be received within the [LayoutRes] by placing a <variable> with name (fm) and
 * type (androidx.fragment.app.FragmentManager).
 */
interface IComponent<DataSource> {
    /**
     * @return layout in which [DataSource] should be bound.
     */
    fun getLayoutRes(): LayoutRes

    /**
     * @return key to which [DataSource] should be mapped.
     */
    fun getBindingKey(): BindingKey

    /**
     * @return [DataSource] which should be bound into [LayoutRes].
     */
    fun getDataSource(): DataSource

    /**
     * Id pattern: <classname>_ID :: <classHash>_<dataSourceHash>
     *
     * @return unique id for each component.
     */
    fun getId(): String =
        "${this::class.simpleName}_ID :: ${this.hashCode()}_${getDataSource().hashCode()}"
}
