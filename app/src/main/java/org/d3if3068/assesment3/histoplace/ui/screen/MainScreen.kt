package org.d3if3068.assesment3.histoplace.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.model.MainViewModel
import org.d3if3068.assesment3.histoplace.model.Tempat
import org.d3if3068.assesment3.histoplace.network.ApiStatus
import org.d3if3068.assesment3.histoplace.ui.theme.AbuAbu
import org.d3if3068.assesment3.histoplace.ui.theme.HistoPlaceTheme
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToScreen: ( String, String, Int, String, List<String>, String, String, String, String) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        tint = WarnaUtama
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            IconButton(
                modifier = Modifier.size(57.dp),
                onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier.size(43.dp),
                    painter = painterResource(id = R.drawable.fab),
                    contentDescription = "fab"
                )
            }
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding), onNavigateToScreen)
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier,
    onNavigateToScreen: (String, String, Int, String, List<String>, String, String, String, String) -> Unit
) {
    val viewModel: MainViewModel = viewModel()
    val data by viewModel.data
    val status by viewModel.status.collectAsState()

    when (status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(1f)
            }
        }

        ApiStatus.SUCCESS -> {
            Column(
                modifier = modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    text = "Cari Tempat Special Mu"
                )
                TextField(
                    shape = RoundedCornerShape(17.dp),
                    leadingIcon = {
                        Image(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = stringResource(R.string.search)
                        )
                    },
                    value = "Search",
                    onValueChange = {},
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 30.dp)
                        .width(190.dp)
                        .height(49.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search),
                            color = Color.White
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = AbuAbu,
                        unfocusedContainerColor = AbuAbu,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                LazyColumn(contentPadding = PaddingValues(bottom = 40.dp)) {
                    items(data) {
                        ListItem(
                            onNavigateToScreen = onNavigateToScreen,
                            tempat = it
                        )
                    }
                }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.error))
                Button(
                    onClick = { viewModel.retrieveData() },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(R.string.try_again))
                }
            }
        }
    }
}

@Composable
fun ListItem(
    tempat: Tempat,
    onNavigateToScreen: (String, String, Int, String, List<String>, String, String, String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .clickable {
                onNavigateToScreen(
                    tempat.imageId,
                    tempat.namaTempat,
                    tempat.rating,
                    tempat.biayaMasuk,
                    tempat.photos,
                    tempat.alamat,
                    tempat.kota,
                    tempat.mapUrl,
                    tempat.catatan
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(17.dp))
                .width(130.dp)
                .height(110.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tempat.imageId)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.size(130.dp),
                contentDescription = tempat.namaTempat,
                contentScale = ContentScale.Crop
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(bottomStart = 50.dp))
                    .background(WarnaUtama)
                    .padding(top = 4.dp)
                    .height(30.dp)
                    .width(70.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .size(18.dp),
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "star",
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    text = "${tempat.rating}/5"
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 8.dp)
        ) {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                text = tempat.namaTempat
            )
            Row(
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            ) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarnaUtama,
                    text = tempat.biayaMasuk
                )
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    text = " / jam"
                )
            }
            Text(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                text = "${tempat.kota}, ${tempat.negara}"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPrev() {
    HistoPlaceTheme {
        MainScreen(
            onNavigateToScreen = {imageId, namaTempat, rating, biayaMasuk, photos, alamat, kota, mapUrl, catatan ->
            },
            rememberNavController()
        )
    }
}