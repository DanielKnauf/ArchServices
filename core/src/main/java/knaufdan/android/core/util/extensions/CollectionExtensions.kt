package knaufdan.android.core.util.extensions

fun Collection<*>.validateIndex(index: Int): Boolean = index in this.indices
