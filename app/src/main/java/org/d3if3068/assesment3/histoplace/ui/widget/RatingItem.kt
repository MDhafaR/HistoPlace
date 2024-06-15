package org.d3if3068.assesment3.histoplace.ui.widget

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.ui.theme.Orange

@Composable
fun RatingItem(index: Int, rating: Int) {
    Icon(
        painter = painterResource(id = R.drawable.star),
        contentDescription = "item star",
        tint = if (index <= rating) Orange else Color.Gray,
        modifier = Modifier.size(18.dp)
        )
}