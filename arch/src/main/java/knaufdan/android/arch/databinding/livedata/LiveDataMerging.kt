package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Uses a [merging] function to combine the value of two [LiveData] sources into a result value which is observed by a created [LiveData] target.
 * Each time one source value changes, the [merging] functions is called to determine the new value which is posted onto the target.
 *
 * @param Target the type of data hold by the returned [LiveData]
 * @param firstSource the first [LiveData] source
 * @param FirstSource the type of data hold by [firstSource]
 * @param secondSource the second [LiveData] source
 * @param SecondSource the type of data hold by [secondSource]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the result posted
 *
 * @return [LiveData] observing the result of the [merging] function
 */
fun <FirstSource, SecondSource, Target> merge(
    firstSource: LiveData<FirstSource>,
    secondSource: LiveData<SecondSource>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSource?, SecondSource?) -> Target
): LiveData<Target> =
    MediatorLiveData<Target>().apply {
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
 * @param Target the type of data hold by the returned [LiveData]
 * @param firstSource the first [LiveData] source
 * @param FirstSource the type of data hold by [firstSource]
 * @param secondSource the second [LiveData] source
 * @param SecondSource the type of data hold by [secondSource]
 * @param thirdSource the second [LiveData] source
 * @param ThirdSource the type of data hold by [thirdSource]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the result posted
 *
 * @return [LiveData] observing the result of the [merging] function
 */
fun <FirstSource, SecondSource, ThirdSource, Target> merge(
    firstSource: LiveData<FirstSource>,
    secondSource: LiveData<SecondSource>,
    thirdSource: LiveData<ThirdSource>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSource?, SecondSource?, ThirdSource?) -> Target
): LiveData<Target> =
    MediatorLiveData<Target>().apply {
        subscribeTo(
            firstSource = firstSource,
            secondSource = secondSource,
            thirdSource = thirdSource,
            distinctUntilChanged = distinctUntilChanged,
            merging = merging
        )
    }
