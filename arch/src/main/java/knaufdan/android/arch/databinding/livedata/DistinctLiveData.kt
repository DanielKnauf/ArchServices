package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.MutableLiveData

/**
 *  A DistinctLiveData is a [MutableLiveData] which compares new values
 *  with its current value by using [equals]. If the DistinctLiveData already
 *  holds the value, the new value is dropped and none of its observers is notified.
 *
 *  A DistinctLiveData provides two method for getting information about its observation state.
 *  [onActivated] marks the beginning of observation (from 0 to 1 observer) and [onInactivated]
 *  marks the end (from 1 to 0 observers). Both callbacks can for example be utilized to
 *  access or close resources only when they are any observers (similar to a cold stream).
 *
 *  @param value set on initialization
 *  @param onActivated is called when first observer starts observing
 *  @param onInactivated is called when last observer stops observing
 */
class DistinctLiveData<T>(
    value: T,
    private val onActivated: DistinctLiveData<T>.() -> Unit = {},
    private val onInactivated: () -> Unit = {}
) : MutableLiveData<T>(value) {
    override fun postValue(value: T) {
        if (this.value == value) {
            return
        }

        super.postValue(value)
    }

    override fun setValue(value: T) {
        if (this.value == value) {
            return
        }

        super.setValue(value)
    }

    override fun onActive() {
        super.onActive()

        onActivated()
    }

    override fun onInactive() {
        onInactivated()

        super.onInactive()
    }
}
