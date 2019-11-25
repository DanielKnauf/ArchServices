package knaufdan.android.arch.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <Source1, Source2, Target> multiMap(
    source1: LiveData<Source1>,
    source2: LiveData<Source2>,
    postOnlyDifferentValues: Boolean = true,
    mapping: (Source1?, Source2?) -> Target
): LiveData<Target> =
        MediatorLiveData<Target>().apply {
            bindTo(
                    source1 = source1,
                    source2 = source2,
                    postOnlyDifferentValues = postOnlyDifferentValues,
                    mapping = mapping
            )
        }

fun <Source1, Source2, Source3, Target> multiMap(
    source1: LiveData<Source1>,
    source2: LiveData<Source2>,
    source3: LiveData<Source3>,
    postOnlyDifferentValues: Boolean = true,
    mapping: (Source1?, Source2?, Source3?) -> Target
): LiveData<Target> =
        MediatorLiveData<Target>().apply {
            bindTo(
                    source1 = source1,
                    source2 = source2,
                    source3 = source3,
                    postOnlyDifferentValues = postOnlyDifferentValues,
                    mapping = mapping
            )
        }
