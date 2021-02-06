package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * [MediatorLiveData] listens to changes of [source]. New values of [MediatorLiveData] are determined by
 * [mapping] function from [SourceType] to [TargetType] and posted asynchronously.
 *
 * @param source [LiveData] added as source
 * @param SourceType type of data hold by [source]
 * @param TargetType type of data hold by target
 * @param distinctUntilChanged if true [mapping] results equal to current value of [MediatorLiveData] are discarded
 * @param mapping function used to determine new value based on [source] value
 */
fun <SourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    source: LiveData<SourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (SourceType) -> TargetType = { value ->
        @Suppress("UNCHECKED_CAST")
        value as TargetType
    }
) {
    addSource(source) { value ->
        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = mapping(value)
        )
    }
}

/**
 * [MediatorLiveData] listens to changes of [firstSource] and [secondSource]. New values of [MediatorLiveData] are
 * determined by [mapping] function from [FirstSourceType] as well as [SecondSourceType] to [TargetType] and
 * posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param TargetType type of data hold by target
 * @param distinctUntilChanged if true [mapping] results equal to current value of [MediatorLiveData] are discarded
 * @param mapping function used to determine new value based on [firstSource] and [secondSource] values
 */
fun <FirstSourceType, SecondSourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (FirstSourceType?, SecondSourceType?) -> TargetType
) {
    addSource(firstSource) { value ->
        val newValue =
            mapping(
                value,
                secondSource.value
            )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }

    addSource(secondSource) { value ->
        val newValue =
            mapping(
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
 * MediatorLiveData] listens to changes of [firstSource], [secondSource] and [thirdSource]. New values of [MediatorLiveData]
 * are determined by [mapping] function from [FirstSourceType], [SecondSourceType] as well as [ThirdSourceType] to [TargetType]
 * and posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param thirdSource [LiveData] added as third source
 * @param ThirdSourceType type of data hold by [thirdSource]
 * @param TargetType type of data hold by target
 * @param distinctUntilChanged if true [mapping] results equal to current value of [MediatorLiveData] are discarded
 * @param mapping function used to determine new value based on [firstSource], [secondSource] and [thirdSource] values
 */
fun <FirstSourceType, SecondSourceType, ThirdSourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    thirdSource: LiveData<ThirdSourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (FirstSourceType?, SecondSourceType?, ThirdSourceType?) -> TargetType
) {
    addSource(firstSource) { value ->
        val newValue =
            mapping(
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
        val newValue =
            mapping(
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
        val newValue =
            mapping(
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
    val value = value ?: postValue(newValue)

    if (distinctUntilChanged && value == newValue) {
        return
    }

    postValue(newValue)
}
