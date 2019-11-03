package knaufdan.android.core

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextProvider @Inject constructor() {
    lateinit var context: Context
}
