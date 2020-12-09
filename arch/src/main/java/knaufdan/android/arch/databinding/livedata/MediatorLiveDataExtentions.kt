package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Adds the [source] to the calling [MediatorLiveData] target. The posted value is determined by the [mapping] function.
 *
 * @param source the added [LiveData] source
 * @param SourceType the type of data hold by [source]
 * @param TargetType the type of data hold by target
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param mapping the function used to determine the value posted
 */
fun <SourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    source: LiveData<SourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (sourceValue: SourceType) -> TargetType = { value ->
        @Suppress("UNCHECKED_CAST")
        value as TargetType
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
 * @param firstSource the first added [LiveData] source
 * @param FirstSourceType the type of data hold by [firstSource]
 * @param secondSource the second added [LiveData] source
 * @param SecondSourceType the type of data hold by [secondSource]
 * @param TargetType the type of data hold by target
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the value posted
 */
fun <FirstSourceType, SecondSourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    distinctUntilChanged: Boolean = true,
    merging: (sourceValue1: FirstSourceType?, sourceValue2: SecondSourceType?) -> TargetType
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
 * @param firstSource the first added [LiveData] source
 * @param FirstSourceType the type of data hold by [firstSource]
 * @param secondSource the second added [LiveData] source
 * @param SecondSourceType the type of data hold by [secondSource]
 * @param thirdSource the second added [LiveData] source
 * @param ThirdSourceType the type of data hold by [thirdSource]
 * @param TargetType the type of data hold by target
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the value posted
 */
fun <FirstSourceType, SecondSourceType, ThirdSourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    thirdSource: LiveData<ThirdSourceType>,
    distinctUntilChanged: Boolean = true,
    merging: (sourceValue1: FirstSourceType?, sourceValue2: SecondSourceType?, sourceValue3: ThirdSourceType?) -> TargetType
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

private fun <TargetType> MediatorLiveData<TargetType>.postValue(
    distinctUntilChanged: Boolean,
    newValue: TargetType
) {
    if (distinctUntilChanged && value == newValue) {
        return
    }

    postValue(newValue)
}
