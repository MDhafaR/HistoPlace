package org.d3if3068.assesment3.histoplace.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.model.Tempat
import org.d3if3068.assesment3.histoplace.ui.theme.AbuGelap
import org.d3if3068.assesment3.histoplace.ui.theme.HistoPlaceTheme
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama
import org.d3if3068.assesment3.histoplace.ui.widget.RatingItem

const val KEY_ID_TEMPAT = "idTempat"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
//    imageId: String,
//    namaTempat: String,
//    rating: Int,
//    biayaMasuk: String,
//    kota: String,
//    catatan: String,
    id: Int? = null,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
//        val data = viewModel.getBook(id) ?: return@LaunchedEffect
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .size(50.dp),
                        onClick = { navController.popBackStack() }) {
                        Image(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.backbutton),
                            contentDescription = "button back",
                        )
                    }
                }
            )
        }
    ) { padding ->
        DetailContent(
            modifier = Modifier.padding(padding),
//            imageId = imageId,
//            namaTempat = namaTempat,
//            rating = rating,
//            biayaMasuk = biayaMasuk,
//            kota = kota,
            id
        )
        if (showDialog) {

        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier,
//    imageId: String,
//    namaTempat: String,
//    rating: Int,
//    biayaMasuk: String,
//    kota: String,
    id: Int? = null
) {
    val context = LocalContext.current
    val mapIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("null")) }

    Box {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(tempat.image_id)
//                .crossfade(true)
//                .build(),
//            modifier = Modifier
//                .fillMaxWidth()
//                .offset(y = (-100).dp),
//            contentScale = ContentScale.Crop,
////            contentDescription = tempat.nama_tempat
//        )
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .height(328.dp)
                ) {}
            }
            item {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 17.dp, topEnd = 17.dp))
                        .background(Color.White)
                        .height(660.dp)
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
//                        Text(
//                            text = tempat.nama_tempat, fontSize = 22.sp, fontWeight = FontWeight.Medium,
//                            color = Color.Black
//                        )
//                        Row(
//                            horizontalArrangement = Arrangement.spacedBy(2.dp)
//                        ) {
//                            arrayOf(1, 2, 3, 4, 5).map { RatingItem(index = it, rating = tempat.rating) }
//                        }
                    }
                    Row(
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        Text(text = "Entry Fee : ", color = Color.Black)
//                        Text(text = tempat.biaya_masuk, color = WarnaUtama, fontWeight = FontWeight.Medium)
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 26.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 12.dp),
                            text = "Photos",
                            color = Color.Black
                        )
                        LazyRow() {
                            item {
                                AsyncImage(
                                    modifier = Modifier
                                        .padding(end = 18.dp)
                                        .clip(shape = RoundedCornerShape(17.dp))
                                        .width(110.dp)
                                        .height(88.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("")
                                        .crossfade(true)
                                        .build(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "",
                                )
                            }
                            item {

                            }
                        }
                    }
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = "Location",
                        color = Color.Black
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.width(300.dp)
                        ) {
                            Text(
                                text = "",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 5.dp),
                                color = AbuGelap,
                            )
//                            Text(text = tempat.kota, fontSize = 14.sp, color = AbuGelap, fontWeight = FontWeight.Medium)
                        }
                        IconButton(modifier = Modifier.size(45.dp), onClick = {
                            context.startActivities(arrayOf(mapIntent))
                        }) {
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "location button"
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(2.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 6.dp),
                            text = "Notes",
                            color = Color.Black
                        )
                        Text(
                            text = "",
                            fontSize = 14.sp,
                            color = AbuGelap,
                        )
                    }
                }
            }
        }
    }
}
