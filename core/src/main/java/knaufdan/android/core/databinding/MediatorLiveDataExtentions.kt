package knaufdan.android.core.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <Target, Source> MediatorLiveData<Target>.bindTo(
    source: LiveData<Source>,
    postOnlyDifferentValues: Boolean = true,
    mapping: (sourceValue: Source) -> Target = { value ->
        @Suppress("UNCHECKED_CAST")
        value as Target
    }
) {
    addSource(source) { sourceValue ->
        val newValue = mapping(sourceValue)

        postValue(
            postOnlyDifferentValues = postOnlyDifferentValues,
            newValue = newValue
        )
    }
}

fun <Target, Source1, Source2, Source3> MediatorLiveData<Target>.bindTo(
    source1: LiveData<Source1>,
    source2: LiveData<Source2>,
    source3: LiveData<Source3>,
    postOnlyDifferentValues: Boolean = true,
    mapping: (sourceValue1: Source1?, sourceValue2: Source2?, sourceValue3: Source3?) -> Target
) {
    addSource(source1) { sourceValue1 ->
        val newValue = mapping(
            sourceValue1,
            source2.value,
            source3.value
        )

        postValue(
            postOnlyDifferentValues = postOnlyDifferentValues,
            newValue = newValue
        )
    }

    addSource(source2) { sourceValue2 ->
        val newValue = mapping(
            source1.value,
            sourceValue2,
            source3.value
        )

        postValue(
            postOnlyDifferentValues = postOnlyDifferentValues,
            newValue = newValue
        )
    }

    addSource(source3) { sourceValue3 ->
        val newValue = mapping(
            source1.value,
            source2.value,
            sourceValue3
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
