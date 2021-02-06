package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

open class SubscribingLiveData<Source, Target>(
    private val source: LiveData<Source>,
    private val distinctUntilChanged: Boolean = true,
    private val mapping: (Source) -> Target = { value ->
        @Suppress("UNCHECKED_CAST")
        value as Target
    }
) : MediatorLiveData<Target>() {
    override fun onActive() {
        super.onActive()

        subscribeTo(
            source = source,
            distinctUntilChanged = distinctUntilChanged,
            mapping = mapping
        )
    }

    override fun onInactive() {
        removeSource(source)

        super.onInactive()
    }
}
