package org.d3if3068.assesment3.histoplace.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3068.assesment3.histoplace.model.MainViewModel
import org.d3if3068.assesment3.histoplace.model.Tempat
import org.d3if3068.assesment3.histoplace.ui.screen.DetailScreen
import org.d3if3068.assesment3.histoplace.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val viewModel: MainViewModel = viewModel()

    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<Tempat> = moshi.adapter(Tempat::class.java)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController, viewModel)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("tempatJson") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType } // Tambahkan userId sebagai argument
            )
        ) { backStackEntry ->
            val tempatJson = backStackEntry.arguments?.getString("tempatJson")
            val userId = backStackEntry.arguments?.getString("userId") // Ambil userId dari arguments
            if (tempatJson != null && userId != null) {
                val tempat = jsonAdapter.fromJson(Uri.decode(tempatJson))
                if (tempat != null) {
                    DetailScreen(navController, tempat, userId, viewModel) // Kirim userId ke DetailScreen
                } else {
                    Log.e("SetupNavGraph", "Failed to parse Tempat from JSON: $tempatJson")
                }
            } else {
                Log.e("SetupNavGraph", "tempatJson or userId is null")
            }
        }

    }
}



