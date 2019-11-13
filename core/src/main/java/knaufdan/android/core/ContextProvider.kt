package knaufdan.android.core

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextProvider @Inject constructor() : IContextProvider {
    private lateinit var context: Context

    override fun setContext(context: Context) {
        this.context = context
    }

    override fun getContext(): Context = context
}
