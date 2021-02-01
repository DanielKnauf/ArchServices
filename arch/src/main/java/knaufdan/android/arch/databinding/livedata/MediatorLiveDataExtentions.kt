package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * [MediatorLiveData] listens to changes of [source]. New values of [MediatorLiveData] are determined by
 * [mapping] function from [Source] to [Target] and posted asynchronously.
 *
 * @param source [LiveData] added as source
 * @param Source type of data hold by [source]
 * @param Target type of data hold by target
 * @param distinctUntilChanged if true [mapping] results equal to current value of [MediatorLiveData] are discarded
 * @param mapping function used to determine new value based on [source] value
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
        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = mapping(value)
        )
    }
}

/**
 * [MediatorLiveData] listens to changes of [firstSource] and [secondSource]. New values of [MediatorLiveData] are
 * determined by [merging] function from [FirstSource] as well as [SecondSource] to [Target] and posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSource type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSource type of data hold by [secondSource]
 * @param Target type of data hold by target
 * @param distinctUntilChanged if true [merging] results equal to current value of [MediatorLiveData] are discarded
 * @param merging function used to determine new value based on [firstSource] and [secondSource] values
 */
fun <FirstSource, SecondSource, Target> MediatorLiveData<Target>.subscribeTo(
    firstSource: LiveData<FirstSource>,
    secondSource: LiveData<SecondSource>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSource?, SecondSource?) -> Target
) {
    addSource(firstSource) { value ->
        val newValue =
            merging(
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
            merging(
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
 * are determined by [merging] function from [FirstSource], [SecondSource] as well as [ThirdSource] to [Target]
 * and posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSource type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSource type of data hold by [secondSource]
 * @param thirdSource [LiveData] added as third source
 * @param ThirdSource type of data hold by [thirdSource]
 * @param Target type of data hold by target
 * @param distinctUntilChanged if true [merging] results equal to current value of [MediatorLiveData] are discarded
 * @param merging function used to determine new value based on [firstSource], [secondSource] and [thirdSource] values
 */
fun <FirstSource, SecondSource, ThirdSource, Target> MediatorLiveData<Target>.subscribeTo(
    firstSource: LiveData<FirstSource>,
    secondSource: LiveData<SecondSource>,
    thirdSource: LiveData<ThirdSource>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSource?, SecondSource?, ThirdSource?) -> Target
) {
    addSource(firstSource) { value ->
        val newValue =
            merging(
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
            merging(
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
            merging(
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
    val value = value ?: postValue(newValue)

    if (distinctUntilChanged && value == newValue) {
        return
    }

    postValue(newValue)
}
