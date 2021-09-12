package danielknauf.livescores

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import knaufdan.android.arch.navigation.navigationController

@BindingAdapter("fragmentManager")
fun BottomNavigationView.bindNavigationController(fragmentManager: FragmentManager) {
    val navController = fragmentManager.navigationController ?: return
    setupWithNavController(navController)
}
