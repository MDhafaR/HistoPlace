package org.d3if3068.assesment3.histoplace.navigation

import org.d3if3068.assesment3.histoplace.ui.screen.KEY_ID_TEMPAT

sealed class Screen(val route: String) {
    data object Home : Screen("MainScren")

    data object Detail : Screen("DetailScreen/{$KEY_ID_TEMPAT}") {
        fun withId(id: Int) = "DetailScreen/$id"
    }
}