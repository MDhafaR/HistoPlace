package org.d3if3068.assesment3.histoplace.navigation

sealed class Screen(val route: String) {
    object Home : Screen("MainScreen")

    data object Detail : Screen("DetailScreen/{tempatJson}/{userId}") {
        fun createRoute(tempatJson: String, userId: String) = "DetailScreen/$tempatJson/$userId"
    }
}

