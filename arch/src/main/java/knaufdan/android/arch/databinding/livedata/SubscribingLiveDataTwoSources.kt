package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class SubscribingLiveDataTwoSources<FirstSource, SecondSource, Target>(
    private val firstSource: () -> LiveData<FirstSource>,
    private val secondSource: () -> LiveData<SecondSource>,
    private val distinctUntilChanged: Boolean = true,
    private val merging: (FirstSource?, SecondSource?) -> Target
) : MediatorLiveData<Target>() {
    private lateinit var activeFirstSource: LiveData<FirstSource>
    private lateinit var activeSecondSource: LiveData<SecondSource>

    override fun onActive() {
        super.onActive()

        activeFirstSource = firstSource()
        activeSecondSource = secondSource()

        subscribeTo(
            firstSource = activeFirstSource,
            secondSource = activeSecondSource,
            distinctUntilChanged = distinctUntilChanged,
            merging = merging
        )
    }

    override fun onInactive() {
        super.onInactive()

        removeSource(activeFirstSource)
        removeSource(activeSecondSource)
    }
}
