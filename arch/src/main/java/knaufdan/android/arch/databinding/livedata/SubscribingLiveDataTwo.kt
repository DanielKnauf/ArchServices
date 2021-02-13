package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * A SubscribingLiveDataTwo is a [MediatorLiveData] which [subscribeTo] changes of [firstSource]
 * and [secondSource] when first active observer is attached as well as removes [firstSource] and
 * [secondSource] when last observer detaches. New values of [SubscribingLiveData] are determined
 * by [mapping] function [FirstSourceType] as well as [SecondSourceType] to [TargetType] and
 * posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param TargetType type of data hold by SubscribingLiveDataTwo
 * @param distinctUntilChanged if true [mapping] results equal to current value are discarded
 * @param mapping function used to determine new value based on [firstSource] and [secondSource] values
 */
class SubscribingLiveDataTwo<FirstSourceType, SecondSourceType, TargetType>(
    private val firstSource: LiveData<FirstSourceType>,
    private val secondSource: LiveData<SecondSourceType>,
    private val distinctUntilChanged: Boolean = true,
    private val mapping: (FirstSourceType?, SecondSourceType?) -> TargetType
) : MediatorLiveData<TargetType>() {
    override fun onActive() {
        super.onActive()

        subscribeTo(
            firstSource = firstSource,
            secondSource = secondSource,
            distinctUntilChanged = distinctUntilChanged,
            mapping = mapping
        )
    }

    override fun onInactive() {
        removeSource(firstSource)
        removeSource(secondSource)

        super.onInactive()
    }
}
