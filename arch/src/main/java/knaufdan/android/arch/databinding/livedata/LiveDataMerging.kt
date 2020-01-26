package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Uses a [merging] function to combine the value of two [LiveData] sources into a result value which is observed by a created [LiveData] target.
 * Each time one source value changes, the [merging] functions is called to determine the new value which is posted onto the target.
 *
 * @param TargetData - the type of data hold by the returned [LiveData]
 * @param firstSource - the first [LiveData] source
 * @param FirstData - the type of data hold by [firstSource]
 * @param secondSource - the second [LiveData] source
 * @param SecondData - the type of data hold by [secondSource]
 * @param postOnlyDifferentValues - when true results matching the current target value are discarded
 * @param merging - the function used to determine the result posted
 *
 * @return [LiveData] observing the result of the [merging] function
 */
fun <FirstData, SecondData, TargetData> merge(
    firstSource: LiveData<FirstData>,
    secondSource: LiveData<SecondData>,
    postOnlyDifferentValues: Boolean = true,
    merging: (FirstData?, SecondData?) -> TargetData
): LiveData<TargetData> =
        MediatorLiveData<TargetData>().apply {
            bindTo(
                    firstSource = firstSource,
                    secondSource = secondSource,
                    postOnlyDifferentValues = postOnlyDifferentValues,
                    merging = merging
            )
        }

/**
 * Uses a [merging] function to combine three [LiveData] sources into a result value which is observed by a created [LiveData] target.
 * Each time one source value changes, the [merging] functions is called to determine the new value which is posted onto the target.
 *
 * @param TargetData - the type of data hold by the returned [LiveData]
 * @param firstSource - the first [LiveData] source
 * @param FirstData - the type of data hold by [firstSource]
 * @param secondSource - the second [LiveData] source
 * @param SecondData - the type of data hold by [secondSource]
 * @param thirdSource - the second [LiveData] source
 * @param ThirdData - the type of data hold by [thirdSource]
 * @param postOnlyDifferentValues - when true results matching the current target value are discarded
 * @param merging - the function used to determine the result posted
 *
 * @return [LiveData] observing the result of the [merging] function
 */
fun <FirstData, SecondData, ThirdData, TargetData> merge(
    firstSource: LiveData<FirstData>,
    secondSource: LiveData<SecondData>,
    thirdSource: LiveData<ThirdData>,
    postOnlyDifferentValues: Boolean = true,
    merging: (FirstData?, SecondData?, ThirdData?) -> TargetData
): LiveData<TargetData> =
        MediatorLiveData<TargetData>().apply {
            bindTo(
                    firstSource = firstSource,
                    secondSource = secondSource,
                    thirdSource = thirdSource,
                    postOnlyDifferentValues = postOnlyDifferentValues,
                    merging = merging
            )
        }
