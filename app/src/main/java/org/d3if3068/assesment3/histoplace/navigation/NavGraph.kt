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
import org.d3if3068.assesment3.histoplace.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ) {
        composable(route = "MainScreen") {
            MainScreen(
                onNavigateToScreen = {imageId, namaTempat, rating, biayaMasuk, photos, alamat, kota, mapUrl, catatan ->
                    val encodedImageId = Uri.encode(imageId)
                    val encodedNamaTempat = Uri.encode(namaTempat)
                    val encodedBiayaMasuk = Uri.encode(biayaMasuk)
                    val encodedPhotos = photos.joinToString(",") { Uri.encode(it) }
                    val encodedAlamat = Uri.encode(alamat)
                    val encodedKota = Uri.encode(kota)
                    val encodedMapUrl = Uri.encode(mapUrl)
                    val encodedCatatan = Uri.encode(catatan)

                    navController.navigate(
                        "DetailScreen/$encodedImageId/$encodedNamaTempat/$rating/$encodedBiayaMasuk/$encodedPhotos/$encodedAlamat/$encodedKota/$encodedMapUrl/$encodedCatatan"
                    )
                },
                navController = navController
            )
        }

        composable(
            route = "DetailScreen/{imageId}/{namaTempat}/{rating}/{biayaMasuk}/{photos}/{alamat}/{kota}/{mapUrl}/{catatan}",
            arguments = listOf(
                navArgument("imageId") { type = NavType.StringType },
                navArgument("namaTempat") { type = NavType.StringType },
                navArgument("rating") { type = NavType.IntType },
                navArgument("biayaMasuk") { type = NavType.StringType },
                navArgument("photos") { type = NavType.StringType },
                navArgument("alamat") { type = NavType.StringType },
                navArgument("kota") { type = NavType.StringType },
                navArgument("mapUrl") { type = NavType.StringType },
                navArgument("catatan") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
            val namaTempat = backStackEntry.arguments?.getString("namaTempat") ?: ""
            val rating = backStackEntry.arguments?.getInt("rating") ?: 0
            val biayaMasuk = backStackEntry.arguments?.getString("biayaMasuk") ?: ""
            val photos = backStackEntry.arguments?.getString("photos")?.split(",")?.map { Uri.decode(it) } ?: listOf()
            val alamat = backStackEntry.arguments?.getString("alamat") ?: ""
            val kota = backStackEntry.arguments?.getString("kota") ?: ""
            val mapUrl = backStackEntry.arguments?.getString("mapUrl") ?: ""
            val catatan = backStackEntry.arguments?.getString("catatan") ?: ""

            DetailScreen(
                imageId = imageId,
                namaTempat = namaTempat,
                rating = rating,
                biayaMasuk = biayaMasuk,
                photos = photos,
                alamat = alamat,
                kota = kota,
                mapUrl = mapUrl,
                catatan = catatan,
                navController
            )
        }
    }
}
