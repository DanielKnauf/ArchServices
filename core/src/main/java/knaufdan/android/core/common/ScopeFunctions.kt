package knaufdan.android.core.common

inline fun <T> T.applyIf(
    condition: Boolean,
    block: T.() -> Unit
): T = if (condition) apply(block) else this
