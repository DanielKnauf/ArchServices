package knaufdan.android.arch.databinding.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import knaufdan.android.core.common.extensions.validateIndex

@MainThread
fun <T> MutableLiveData<List<T>>.add(
    newItem: T,
    index: Int = UNSPECIFIED_INDEX
) {
    val items = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !items.validateIndex(index)
    if (isInvalidIndex) {
        items.add(newItem)
        value = items
        return
    }

    items.add(
        index = index,
        element = newItem
    )

    value = items
}

@MainThread
fun <T> MutableLiveData<List<T>>.addAll(
    vararg newItems: T,
    index: Int = UNSPECIFIED_INDEX
) {
    val items = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !items.validateIndex(index)
    if (isInvalidIndex) {
        items.addAll(newItems)
        value = items
        return
    }

    items.addAll(
        index = index,
        elements = newItems.toList()
    )

    value = items
}

@MainThread
fun <T> MutableLiveData<List<T>>.remove(index: Int) {
    val items = value?.toMutableList() ?: return

    val isInvalidIndex = !items.validateIndex(index)
    if (isInvalidIndex) {
        return
    }

    items.removeAt(index)

    value = items
}

@MainThread
fun <T> MutableLiveData<List<T>>.clear() {
    value = emptyList()
}

private const val UNSPECIFIED_INDEX = -1
