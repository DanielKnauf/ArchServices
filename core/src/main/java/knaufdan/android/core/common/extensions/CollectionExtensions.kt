package knaufdan.android.core.common.extensions

fun Collection<*>.hasIndex(index: Int): Boolean = index in this.indices

inline fun <reified T> T.toArray(): Array<T> = arrayOf(this)

fun <T> T.toList(): List<T> = listOf(this)
