package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Adds the [source] to the calling [MediatorLiveData] target. The posted value is determined by the [mapping] function.
 *
 * @param TargetData the type of data hold by target
 * @param source the added [LiveData] source
 * @param SourceData the type of data hold by [source]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param mapping the function used to determine the value posted
 */
fun <TargetData, SourceData> MediatorLiveData<TargetData>.subscribeTo(
    source: LiveData<SourceData>,
    distinctUntilChanged: Boolean = true,
    mapping: (sourceValue: SourceData) -> TargetData = { value ->
        @Suppress("UNCHECKED_CAST")
        value as TargetData
    }
) {
    addSource(source) { value ->
        val newValue = mapping(value)

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }
}

/**
 * Adds all sources to the calling [MediatorLiveData] target. The posted value is determined by the [merging] function.
 *
 * @param TargetData the type of data hold by target
 * @param firstSource the first added [LiveData] source
 * @param FirstData the type of data hold by [firstSource]
 * @param secondSource the second added [LiveData] source
 * @param SecondData the type of data hold by [secondSource]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the value posted
 */
fun <TargetData, FirstData, SecondData> MediatorLiveData<TargetData>.subscribeTo(
    firstSource: LiveData<FirstData>,
    secondSource: LiveData<SecondData>,
    distinctUntilChanged: Boolean = true,
    merging: (sourceValue1: FirstData?, sourceValue2: SecondData?) -> TargetData
) {
    addSource(firstSource) { value ->
        val newValue = merging(
            value,
            secondSource.value
        )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }

    addSource(secondSource) { value ->
        val newValue = merging(
            firstSource.value,
            value
        )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }
}

/**
 * Adds all sources to the calling [MediatorLiveData] target. The posted value is determined by the [merging] function.
 *
 * @param TargetData the type of data hold by target
 * @param firstSource the first added [LiveData] source
 * @param FirstData the type of data hold by [firstSource]
 * @param secondSource the second added [LiveData] source
 * @param SecondData the type of data hold by [secondSource]
 * @param thirdSource the second added [LiveData] source
 * @param ThirdData the type of data hold by [thirdSource]
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the value posted
 */
fun <TargetData, FirstData, SecondData, ThirdData> MediatorLiveData<TargetData>.subscribeTo(
    firstSource: LiveData<FirstData>,
    secondSource: LiveData<SecondData>,
    thirdSource: LiveData<ThirdData>,
    distinctUntilChanged: Boolean = true,
    merging: (sourceValue1: FirstData?, sourceValue2: SecondData?, sourceValue3: ThirdData?) -> TargetData
) {
    addSource(firstSource) { value ->
        val newValue = merging(
            value,
            secondSource.value,
            thirdSource.value
        )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
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
            distinctUntilChanged = distinctUntilChanged,
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
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }
}

private fun <Target> MediatorLiveData<Target>.postValue(
    distinctUntilChanged: Boolean,
    newValue: Target
) {
    if (distinctUntilChanged && value == newValue) {
        return
    }

    postValue(newValue)
}
