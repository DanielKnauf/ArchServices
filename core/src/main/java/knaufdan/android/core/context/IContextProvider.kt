package knaufdan.android.core.context

import android.content.Context

interface IContextProvider {

    fun setContext(context: Context)

    fun getContext(): Context
}
