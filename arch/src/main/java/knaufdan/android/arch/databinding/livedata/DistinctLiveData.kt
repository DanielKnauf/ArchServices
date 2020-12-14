package knaufdan.android.arch.databinding.livedata

import androidx.lifecycle.MutableLiveData

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
