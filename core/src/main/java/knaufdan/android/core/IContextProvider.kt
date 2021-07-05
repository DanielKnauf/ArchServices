package knaufdan.android.core

import android.content.Context

interface IContextProvider {
    fun setContext(context: Context)

    fun getContext(): Context
}
