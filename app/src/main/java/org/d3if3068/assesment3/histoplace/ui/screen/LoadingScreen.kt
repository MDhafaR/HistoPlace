package org.d3if3068.assesment3.histoplace.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.ui.theme.AbuGelap
import org.d3if3068.assesment3.histoplace.ui.theme.HistoPlaceTheme
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama
import org.d3if3068.assesment3.histoplace.ui.widget.LoadingAnimation

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(34.dp)
        ) {
            Text(
                modifier = Modifier.padding(top =30.dp , bottom = 10.dp),
                text = stringResource(R.string.judul_loading),
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = stringResource(R.string.cerita_monas),
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = AbuGelap
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.loading), color = WarnaUtama)
                LoadingAnimation()
            }
        }
        Image(
            modifier = Modifier.padding(top = 300.dp).size(680.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.gambarload), contentDescription = "monas"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPrev() {
    HistoPlaceTheme {
        LoadingScreen()
    }
}