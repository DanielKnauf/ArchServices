package knaufdan.android.arch.navigation.implementation

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("fragmentManager")
fun BottomNavigationView.bindNavigationController(fragmentManager: FragmentManager) {
    val navController = fragmentManager.navigationController ?: return
    setupWithNavController(navController)
}
