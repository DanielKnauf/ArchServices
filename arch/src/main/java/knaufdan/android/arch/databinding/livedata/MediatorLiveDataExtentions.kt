package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Adds the [source] to the calling [MediatorLiveData] target. The posted value is determined by the [mapping] function.
 *
 * @param TargetData - the type of data hold by target.
 * @param source - the added [LiveData] source
 * @param SourceData - the type of data hold by [source]
 * @param postOnlyDifferentValues - when true results matching the current target value are discarded
 * @param mapping - the function used to determine the value posted
 */
fun <TargetData, SourceData> MediatorLiveData<TargetData>.bindTo(
    source: LiveData<SourceData>,
    postOnlyDifferentValues: Boolean = true,
    mapping: (sourceValue: SourceData) -> TargetData = { value ->
        @Suppress("UNCHECKED_CAST")
        value as TargetData
    }
) {
    addSource(source) { value ->
        val newValue = mapping(value)

        postValue(
                postOnlyDifferentValues = postOnlyDifferentValues,
                newValue = newValue
        )
    }
}

/**
 * Adds all sources to the calling [MediatorLiveData] target. The posted value is determined by the [merging] function.
 *
 * @param TargetData - the type of data hold by target.
 * @param firstSource - the first added [LiveData] source
 * @param FirstData - the type of data hold by [firstSource]
 * @param secondSource - the second added [LiveData] source
 * @param SecondData - the type of data hold by [secondSource]
 * @param postOnlyDifferentValues - when true results matching the current target value are discarded
 * @param merging - the function used to determine the value posted
 */
fun <TargetData, FirstData, SecondData> MediatorLiveData<TargetData>.bindTo(
    firstSource: LiveData<FirstData>,
    secondSource: LiveData<SecondData>,
    postOnlyDifferentValues: Boolean = true,
    merging: (sourceValue1: FirstData?, sourceValue2: SecondData?) -> TargetData
) {
    addSource(firstSource) { value ->
        val newValue = merging(
                value,
                secondSource.value
        )

        postValue(
                postOnlyDifferentValues = postOnlyDifferentValues,
                newValue = newValue
        )
    }

    addSource(secondSource) { value ->
        val newValue = merging(
                firstSource.value,
                value
        )

        postValue(
                postOnlyDifferentValues = postOnlyDifferentValues,
                newValue = newValue
        )
    }
}

/**
 * Adds all sources to the calling [MediatorLiveData] target. The posted value is determined by the [merging] function.
 *
 * @param TargetData - the type of data hold by target.
 * @param firstSource - the first added [LiveData] source
 * @param FirstData - the type of data hold by [firstSource]
 * @param secondSource - the second added [LiveData] source
 * @param SecondData - the type of data hold by [secondSource]
 * @param thirdSource - the second added [LiveData] source
 * @param ThirdData- the type of data hold by [thirdSource]
 * @param postOnlyDifferentValues - when true results matching the current target value are discarded
 * @param merging - the function used to determine the value posted
 */
fun <TargetData, FirstData, SecondData, ThirdData> MediatorLiveData<TargetData>.bindTo(
    firstSource: LiveData<FirstData>,
    secondSource: LiveData<SecondData>,
    thirdSource: LiveData<ThirdData>,
    postOnlyDifferentValues: Boolean = true,
    merging: (sourceValue1: FirstData?, sourceValue2: SecondData?, sourceValue3: ThirdData?) -> TargetData
) {
    addSource(firstSource) { value ->
        val newValue = merging(
                value,
                secondSource.value,
                thirdSource.value
        )

        postValue(
                postOnlyDifferentValues = postOnlyDifferentValues,
                newValue = newValue
        )
    }

    addSource(secondSource) { value ->
        val newValue = merging(
                firstSource.value,
                value,
                thirdSource.value
        )

        postValue(
                postOnlyDifferentValues = postOnlyDifferentValues,
                newValue = newValue
        )
    }

    addSource(thirdSource) { value ->
        val newValue = merging(
                firstSource.value,
                secondSource.value,
                value
        )

        postValue(
                postOnlyDifferentValues = postOnlyDifferentValues,
                newValue = newValue
        )
    }
}

private fun <Target> MediatorLiveData<Target>.postValue(
    postOnlyDifferentValues: Boolean,
    newValue: Target
) {
    if (postOnlyDifferentValues && value == newValue) {
        return
    }

    postValue(newValue)
}
