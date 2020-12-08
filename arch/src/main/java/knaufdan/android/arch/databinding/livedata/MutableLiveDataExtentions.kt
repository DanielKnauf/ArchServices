package knaufdan.android.arch.databinding.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import knaufdan.android.core.common.extensions.validateIndex

@MainThread
fun <T> MutableLiveData<List<T>>.add(
    element: T,
    index: Int = UNSPECIFIED_INDEX
) {
    val list = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {
        list.add(element)

        value = list

        return
    }

    list.add(
        index = index,
        element = element
    )

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.addAll(
    vararg elements: T,
    index: Int = UNSPECIFIED_INDEX
) {
    val list = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {
        list.addAll(elements)

        value = list

        return
    }

    list.addAll(
        index = index,
        elements = elements.toList()
    )

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.addAll(
    elements: List<T>,
    index: Int = UNSPECIFIED_INDEX
) {
    val list = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {

        list.addAll(elements)

        value = list

        return
    }

    list.addAll(
        index = index,
        elements = elements
    )

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.remove(index: Int) {
    val list = value?.toMutableList() ?: return

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {
        return
    }

    list.removeAt(index)

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.clear() {
    value = emptyList()
}

@MainThread
fun <T> MutableLiveData<List<T>>.forEachIndexed(
    action: (index: Int, item: T) -> Unit
) {
    value?.forEachIndexed(action)
}

@MainThread
fun <T> MutableLiveData<List<T>>.isEmpty(): Boolean = value?.isEmpty() ?: true

@MainThread
fun <T> MutableLiveData<List<T>>.lastIndex(): Int = if (isEmpty()) 0 else value?.lastIndex ?: 0

private const val UNSPECIFIED_INDEX = -1
