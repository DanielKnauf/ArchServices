package knaufdan.android.core.context.implementation

import android.content.Context
import knaufdan.android.core.context.IContextProvider

internal class ContextProvider : IContextProvider {

    private lateinit var context: Context

    override fun setContext(context: Context) {
        this.context = context
    }

    override fun getContext(): Context = context
}
