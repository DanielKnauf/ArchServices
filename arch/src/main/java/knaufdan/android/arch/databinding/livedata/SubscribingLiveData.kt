package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * A SubscribingLiveData is a [MediatorLiveData] which [subscribeTo] changes of [source] when
 * first active observer is attached as well as removes [source] when last observer detaches.
 * New values of [SubscribingLiveData] are determined by [mapping] function from [SourceType]
 * to [TargetType] and posted asynchronously.
 *
 * NOTE: Per default the [SourceType] is cast directly into the [TargetType] without manipulation.
 *
 * @param source [LiveData] added as source
 * @param SourceType type of data hold by [source]
 * @param TargetType type of data hold by SubscribingLiveData
 * @param distinctUntilChanged if true [mapping] results equal to current value are discarded
 * @param mapping function used to determine new value based on [source] value
 */
class SubscribingLiveData<SourceType, TargetType>(
    private val source: LiveData<SourceType>,
    private val distinctUntilChanged: Boolean = true,
    private val mapping: (SourceType) -> TargetType = { value ->
        @Suppress("UNCHECKED_CAST")
        value as TargetType
    }
) : MediatorLiveData<TargetType>() {
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
