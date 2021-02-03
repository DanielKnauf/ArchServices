package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Creates a new [LiveData] [subscribeTo] [firstSource] and [secondSource]. Each time one source value changes,
 * new values are determined by [merging] [FirstSourceType] and [SecondSourceType] into [TargetType] and posted
 * asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param TargetType type of data hold by the returned [LiveData]
 * @param distinctUntilChanged if true [merging] results equal to current value of returned [LiveData] are discarded
 * @param merging function used to determine new value based on [firstSource] and [secondSource] values
 *
 * @return [LiveData] containing the result of [merging] values of [firstSource] and [secondSource]
 */
fun <FirstSourceType, SecondSourceType, TargetType> merge(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSourceType?, SecondSourceType?) -> TargetType
): LiveData<TargetType> =
    MediatorLiveData<TargetType>().apply {
        subscribeTo(
            firstSource = firstSource,
            secondSource = secondSource,
            distinctUntilChanged = distinctUntilChanged,
            merging = merging
        )
    }

/**
 * Creates a new [LiveData] [subscribeTo] [firstSource], [secondSource] and [thirdSource]. Each time one source value changes,
 * new values are determined by [merging] [FirstSourceType], [SecondSourceType] and [ThirdSourceType] into [TargetType] and posted
 * asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param thirdSource [LiveData] added as third source
 * @param ThirdSourceType type of data hold by [thirdSource]
 * @param TargetType type of data hold by the returned [LiveData]
 * @param distinctUntilChanged if true [merging] results equal to current value of returned [LiveData] are discarded
 * @param merging function used to determine new value based on [firstSource], [secondSource] and [thirdSource] values
 *
 * @return [LiveData] containing the result of [merging] values of [firstSource], [secondSource] and [thirdSource]
 */
fun <FirstSourceType, SecondSourceType, ThirdSourceType, TargetType> merge(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    thirdSource: LiveData<ThirdSourceType>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSourceType?, SecondSourceType?, ThirdSourceType?) -> TargetType
): LiveData<TargetType> =
    MediatorLiveData<TargetType>().apply {
        subscribeTo(
            firstSource = firstSource,
            secondSource = secondSource,
            thirdSource = thirdSource,
            distinctUntilChanged = distinctUntilChanged,
            merging = merging
        )
    }
