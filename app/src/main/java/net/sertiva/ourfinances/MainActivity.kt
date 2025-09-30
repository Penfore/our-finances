package net.sertiva.ourfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.sertiva.ourfinances.ui.screen.AddTransactionScreen
import net.sertiva.ourfinances.ui.screen.HomeScreen
import net.sertiva.ourfinances.ui.theme.OurFinancesTheme
import net.sertiva.ourfinances.ui.viewmodel.AddTransactionViewModel
import net.sertiva.ourfinances.ui.viewmodel.HomeViewModel
import net.sertiva.ourfinances.utils.DependencyFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OurFinancesTheme {
                OurFinancesApp()
            }
        }
    }
}

@Composable
fun OurFinancesApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeViewModel = viewModel<HomeViewModel>(
                factory = viewModelFactory {
                    addInitializer(HomeViewModel::class) {
                        DependencyFactory.createHomeViewModel(this[APPLICATION_KEY]!!)
                    }
                }
            )

            HomeScreen(
                viewModel = homeViewModel,
                onAddTransactionClick = {
                    navController.navigate("add_transaction")
                }
            )
        }

        composable("add_transaction") {
            val addTransactionViewModel = viewModel<AddTransactionViewModel>(
                factory = viewModelFactory {
                    addInitializer(AddTransactionViewModel::class) {
                        DependencyFactory.createAddTransactionViewModel(this[APPLICATION_KEY]!!)
                    }
                }
            )

            AddTransactionScreen(
                viewModel = addTransactionViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OurFinancesAppPreview() {
    OurFinancesTheme {
        OurFinancesApp()
    }
}
