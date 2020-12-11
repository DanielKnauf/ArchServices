package knaufdan.android.core.common.extensions

fun Collection<*>.validateIndex(index: Int): Boolean = index in this.indices

fun <T> T.toList(): List<T> = listOf(this)
