@file:Suppress("unused")

package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Creates a new [LiveData] subscribing to [source]. Each time the source value changes, new values is determined
 * by [mapping] [SourceType] to [TargetType] and posted asynchronously.
 *
 * @param source [LiveData] added as source
 * @param SourceType type of data hold by [source]
 * @param TargetType type of data hold by the returned [LiveData]
 * @param distinctUntilChanged if true [mapping] results equal to current value of returned [LiveData] are discarded
 * @param mapping function used to determine new value based on [source] value
 *
 * @return [LiveData] containing the result of [mapping] values of [source]
 */
fun <SourceType, TargetType> map(
    source: LiveData<SourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (SourceType) -> TargetType
): LiveData<TargetType> =
    SubscribingLiveData(
        source = { source },
        distinctUntilChanged = distinctUntilChanged,
        mapping = mapping
    )

/**
 * Creates a new [LiveData] subscribing to [firstSource] and [secondSource]. Each time one source value changes,
 * new values are determined by [mapping] [FirstSourceType] and [SecondSourceType] to [TargetType] and posted
 * asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param TargetType type of data hold by the returned [LiveData]
 * @param distinctUntilChanged if true [mapping] results equal to current value of returned [LiveData] are discarded
 * @param mapping function used to determine new value based on [firstSource] and [secondSource] values
 *
 * @return [LiveData] containing the result of [mapping] values of [firstSource] and [secondSource]
 */
fun <FirstSourceType, SecondSourceType, TargetType> map(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (FirstSourceType?, SecondSourceType?) -> TargetType
): LiveData<TargetType> =
    SubscribingLiveDataTwoSources(
        firstSource = { firstSource },
        secondSource = { secondSource },
        distinctUntilChanged = distinctUntilChanged,
        mapping = mapping
    )

/**
 * Creates a new [LiveData] subscribing to [firstSource], [secondSource] and [thirdSource]. Each time one source value changes,
 * new values are determined by [mapping] [FirstSourceType], [SecondSourceType] and [ThirdSourceType] to [TargetType] and
 * posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param thirdSource [LiveData] added as third source
 * @param ThirdSourceType type of data hold by [thirdSource]
 * @param TargetType type of data hold by the returned [LiveData]
 * @param distinctUntilChanged if true [mapping] results equal to current value of returned [LiveData] are discarded
 * @param mapping function used to determine new value based on [firstSource], [secondSource] and [thirdSource] values
 *
 * @return [LiveData] containing the result of [mapping] values of [firstSource], [secondSource] and [thirdSource]
 */
fun <FirstSourceType, SecondSourceType, ThirdSourceType, TargetType> map(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    thirdSource: LiveData<ThirdSourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (FirstSourceType?, SecondSourceType?, ThirdSourceType?) -> TargetType
): LiveData<TargetType> =
    MediatorLiveData<TargetType>().apply {
        subscribeTo(
            firstSource = firstSource,
            secondSource = secondSource,
            thirdSource = thirdSource,
            distinctUntilChanged = distinctUntilChanged,
            mapping = mapping
        )
    }
