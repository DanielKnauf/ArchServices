package knaufdan.android.core.common.extensions

fun Collection<*>.validateIndex(index: Int): Boolean = index in this.indices
