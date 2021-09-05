package knaufdan.android.core

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

interface IContextProvider {

    val fragmentManager: FragmentManager?
        get() = (getContext() as? AppCompatActivity)?.supportFragmentManager

    fun setContext(context: Context)

    fun getContext(): Context
}
