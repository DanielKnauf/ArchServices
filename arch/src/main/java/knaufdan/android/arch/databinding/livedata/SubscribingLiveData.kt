package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

open class SubscribingLiveData<Source, Target>(
    private val source: () -> LiveData<Source>,
    private val mapping: (Source) -> Target = { value ->
        @Suppress("UNCHECKED_CAST")
        value as Target
    }
) : MediatorLiveData<Target>() {
    private lateinit var activeSource: LiveData<Source>

    override fun onActive() {
        super.onActive()

        activeSource = source()

        subscribeTo(
            source = activeSource,
            mapping = mapping
        )
    }

    override fun onInactive() {
        super.onInactive()

        removeSource(activeSource)
    }
}
