package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class SubscribingLiveDataTwoSources<FirstSource, SecondSource, Target>(
    private val firstSource: LiveData<FirstSource>,
    private val secondSource: LiveData<SecondSource>,
    private val distinctUntilChanged: Boolean = true,
    private val mapping: (FirstSource?, SecondSource?) -> Target
) : MediatorLiveData<Target>() {
    override fun onActive() {
        super.onActive()

        subscribeTo(
            firstSource = firstSource,
            secondSource = secondSource,
            distinctUntilChanged = distinctUntilChanged,
            mapping = mapping
        )
    }

    override fun onInactive() {
        removeSource(firstSource)
        removeSource(secondSource)

        super.onInactive()
    }
}
