package knaufdan.android.arch.navigation

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

val FragmentManager.navigationController: NavController?
    get() = (fragments.firstOrNull() as? NavHostFragment)?.navController
