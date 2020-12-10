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
 * Adds all sources to the calling [MediatorLiveData] target. The posted value is determined by the [merging] function.
 *
 * @param firstSource the first added [LiveData] source
 * @param FirstSource the type of data hold by [firstSource]
 * @param secondSource the second added [LiveData] source
 * @param SecondSource the type of data hold by [secondSource]
 * @param Target the type of data hold by target
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the value posted
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
 * Adds all sources to the calling [MediatorLiveData] target. The posted value is determined by the [merging] function.
 *
 * @param firstSource the first added [LiveData] source
 * @param FirstSource the type of data hold by [firstSource]
 * @param secondSource the second added [LiveData] source
 * @param SecondSource the type of data hold by [secondSource]
 * @param thirdSource the second added [LiveData] source
 * @param ThirdSource the type of data hold by [thirdSource]
 * @param Target the type of data hold by target
 * @param distinctUntilChanged if true results equal to current value of [MediatorLiveData] target are discarded
 * @param merging the function used to determine the value posted
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
    if (distinctUntilChanged && value == newValue) {
        return
    }

    postValue(newValue)
}
