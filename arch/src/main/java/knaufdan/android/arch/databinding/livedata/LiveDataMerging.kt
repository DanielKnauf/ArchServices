package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Uses a [merging] function to combine the value of two [LiveData] sources into a result value which is observed by a created [LiveData] target.
 * Each time one source value changes, the [merging] functions is called to determine the new value which is posted onto the target.
 *
 * @param TargetType the type of data hold by the returned [LiveData]
 * @param firstSource the first [LiveData] source
 * @param FirstSourceType the type of data hold by [firstSource]
 * @param secondSource the second [LiveData] source
 * @param SecondSourceType the type of data hold by [secondSource]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the result posted
 *
 * @return [LiveData] observing the result of the [merging] function
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
 * Uses a [merging] function to combine three [LiveData] sources into a result value which is observed by a created [LiveData] target.
 * Each time one source value changes, the [merging] functions is called to determine the new value which is posted onto the target.
 *
 * @param TargetType the type of data hold by the returned [LiveData]
 * @param firstSource the first [LiveData] source
 * @param FirstSourceType the type of data hold by [firstSource]
 * @param secondSource the second [LiveData] source
 * @param SecondSourceType the type of data hold by [secondSource]
 * @param thirdSource the second [LiveData] source
 * @param ThirdSourceType the type of data hold by [thirdSource]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the result posted
 *
 * @return [LiveData] observing the result of the [merging] function
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
