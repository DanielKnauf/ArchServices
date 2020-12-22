package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.MutableLiveData

/**
 *  A DistinctLiveData is a [MutableLiveData] which compares new values
 *  with its current value by using [equals]. If the DistinctLiveData already
 *  holds the value, the new value is dropped and none of its observers is notified.
 */
class DistinctLiveData<T>(value: T) : MutableLiveData<T>(value) {
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
}
