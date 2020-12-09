package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class SubscribingLiveData<SourceType, TargetType>(
    private val source: LiveData<SourceType>,
    private val mapping: (SourceType) -> TargetType = { value ->
        @Suppress("UNCHECKED_CAST")
        value as TargetType
    }
) : MediatorLiveData<TargetType>() {
    override fun onActive() {
        super.onActive()

        subscribeTo(
            source = source,
            mapping = mapping
        )
    }

    override fun onInactive() {
        super.onInactive()

        removeSource(source)
    }
}
