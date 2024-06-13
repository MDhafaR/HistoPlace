package org.d3if3068.assesment3.histoplace.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3068.assesment3.histoplace.ui.screen.DetailScreen
import org.d3if3068.assesment3.histoplace.ui.screen.KEY_ID_TEMPAT
import org.d3if3068.assesment3.histoplace.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }

        // ini adalah navigation ke page Detail dengan menggunakan id dari mainScreen
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(KEY_ID_TEMPAT) { type = NavType.IntType },
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(KEY_ID_TEMPAT)
            DetailScreen(id, navController)
        }
//        composable(route = "MainScreen") {
//            MainScreen(
//                onNavigateToScreen = { imageId, namaTempat, rating, biayaMasuk, kota, catatan ->
//                    val encodedImageId = Uri.encode(imageId)
//                    val encodedNamaTempat = Uri.encode(namaTempat)
//                    val encodedBiayaMasuk = Uri.encode(biayaMasuk)
//                    val encodedKota = Uri.encode(kota)
//                    val encodedCatatan = Uri.encode(catatan)
//
//                    navController.navigate(
//                        "DetailScreen/$encodedImageId/$encodedNamaTempat/$rating/$encodedBiayaMasuk/$encodedKota/$encodedCatatan"
//                    )
//                },
//                navController = navController
//            )
//        }
//
//        composable(
//            route = "DetailScreen/{imageId}/{namaTempat}/{rating}/{biayaMasuk}/{kota}/{catatan}",
//            arguments = listOf(
//                navArgument("imageId") { type = NavType.StringType },
//                navArgument("namaTempat") { type = NavType.StringType },
//                navArgument("rating") { type = NavType.IntType },
//                navArgument("biayaMasuk") { type = NavType.StringType },
//                navArgument("kota") { type = NavType.StringType },
//                navArgument("catatan") { type = NavType.StringType },
//            )
//        ) { backStackEntry ->
//            val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
//            val namaTempat = backStackEntry.arguments?.getString("namaTempat") ?: ""
//            val rating = backStackEntry.arguments?.getInt("rating") ?: 0
//            val biayaMasuk = backStackEntry.arguments?.getString("biayaMasuk") ?: ""
//            val kota = backStackEntry.arguments?.getString("kota") ?: ""
//            val catatan = backStackEntry.arguments?.getString("catatan") ?: ""
//
//            DetailScreen(
//                imageId = imageId,
//                namaTempat = namaTempat,
//                rating = rating,
//                biayaMasuk = biayaMasuk,
//                kota = kota,
//                catatan = catatan,
//                navController
//            )
//        }
    }
}
