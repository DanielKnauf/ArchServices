package knaufdan.android.arch.base.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ComponentFactory {
    fun createMessageComponent(layoutRes: LayoutRes): IComponent<Unit> =
        object : IMessageComponent {
            override fun getLayoutRes(): LayoutRes = layoutRes
        }

    fun createDisplayComponent(
        layoutRes: LayoutRes,
        bindingKey: BindingKey,
        displayText: String
    ) = createDisplayComponent(
        layoutRes,
        bindingKey,
        MutableLiveData(displayText)
    )

    fun createDisplayComponent(
        layoutRes: LayoutRes,
        bindingKey: BindingKey,
        displayText: LiveData<String>
    ) = object : IComponent<TextDataSource> {
        override fun getLayoutRes(): LayoutRes = layoutRes

        override fun getBindingKey(): BindingKey = bindingKey

        override fun getDataSource(): TextDataSource = TextDataSource(displayText)
    }
}
