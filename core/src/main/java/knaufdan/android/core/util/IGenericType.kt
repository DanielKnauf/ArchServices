package knaufdan.android.core.util

import java.lang.reflect.ParameterizedType

interface IGenericType <Type> {
    /**
     * @return class of [Type]
     */
    @Suppress("UNCHECKED_CAST")
    fun getTypeClass(): Class<Type> =
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<Type>
}
