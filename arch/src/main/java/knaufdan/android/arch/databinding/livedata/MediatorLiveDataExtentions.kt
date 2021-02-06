package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Adds the [source] to the calling [MediatorLiveData] target. The posted value is determined by the [mapping] function.
 *
 * @param source the added [LiveData] source
 * @param Source the type of data hold by [source]
 * @param Target the type of data hold by target
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param mapping the function used to determine the value posted
 */
fun <Source, Target> MediatorLiveData<Target>.subscribeTo(
    source: LiveData<Source>,
    distinctUntilChanged: Boolean = true,
    mapping: (Source) -> Target = { value ->
        @Suppress("UNCHECKED_CAST")
        value as Target
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
fun <FirstSource, SecondSource, Target> MediatorLiveData<Target>.subscribeTo(
    firstSource: LiveData<FirstSource>,
    secondSource: LiveData<SecondSource>,
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
fun <FirstSource, SecondSource, ThirdSource, Target> MediatorLiveData<Target>.subscribeTo(
    firstSource: LiveData<FirstSource>,
    secondSource: LiveData<SecondSource>,
    thirdSource: LiveData<ThirdSource>,
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

private fun <Target> MediatorLiveData<Target>.postValue(
    distinctUntilChanged: Boolean,
    newValue: Target
) {
    if (distinctUntilChanged && value == newValue) {
        return
    }

    postValue(newValue)
}
