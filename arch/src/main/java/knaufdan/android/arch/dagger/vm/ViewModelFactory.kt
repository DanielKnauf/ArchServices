package knaufdan.android.arch.dagger.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider: Provider<out ViewModel>? = providers[modelClass] ?: modelClass.toProvider()

        checkNotNull(provider) { "Could not find a provider for $modelClass" }

        try {
            @Suppress("UNCHECKED_CAST")
            return provider.get() as T
        } catch (e: Exception) {
            throw ClassCastException("Could not cast ${provider.get()} to given type $modelClass")
        }
    }

    private fun <T : ViewModel> Class<T>.toProvider(): Provider<out ViewModel>? =
        providers.entries.firstOrNull { entry ->
            this.isAssignableFrom(entry.key)
        }?.value
}
