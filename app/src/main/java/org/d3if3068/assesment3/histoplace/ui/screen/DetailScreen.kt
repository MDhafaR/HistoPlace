package org.d3if3068.assesment3.histoplace.ui.screen

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.model.MainViewModel
import org.d3if3068.assesment3.histoplace.model.Tempat
import org.d3if3068.assesment3.histoplace.network.ApiStatus
import org.d3if3068.assesment3.histoplace.network.TempatApi
import org.d3if3068.assesment3.histoplace.ui.theme.AbuGelap
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama
import org.d3if3068.assesment3.histoplace.ui.widget.LoadingAnimation
import org.d3if3068.assesment3.histoplace.ui.widget.NextInputDialog
import org.d3if3068.assesment3.histoplace.ui.widget.PhotosDialog
import org.d3if3068.assesment3.histoplace.ui.widget.RatingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    tempat: Tempat,
    userId: String,
    viewModel: MainViewModel
) {
    var showDialog by remember { mutableStateOf(true) }

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
            tempat,
            viewModel,
            navController
        )
        if (tempat.catatan == null) {
            if (showDialog) {
                NextInputDialog(
                    onDismissRequest = {
                        showDialog = false
                        navController.popBackStack()
                    },
                ) { alamat, catatan ->
                    viewModel.saveDetail(alamat, catatan, tempat.id, userId)
                    showDialog = false
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier,
    tempat: Tempat,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val encodeMap = tempat.nama_tempat.replace(" ", "+")
    val mapIntent =
        remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=$encodeMap")) }
    val data by viewModel.dataPhotos
    val status by viewModel.statusPhotos.collectAsState()

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(CropImageContract()) {
        bitmap = getCroppedImage(context.contentResolver, it)
        viewModel.savePhotos(bitmap!!, tempat.id)
        navController.popBackStack()
    }
    var showInputDialog by remember { mutableStateOf(false) }

    LaunchedEffect(tempat.id) {
        viewModel.retrievePhotos(tempat.id)
    }

    var gambarSaatIni: String by remember { mutableStateOf("") }
    var idSaatIni: String by remember { mutableStateOf("") }
    var tempatIdSaatIni: String by remember { mutableStateOf("") }

    if (showInputDialog) {
        PhotosDialog(
            id = idSaatIni,
            tempatId = tempatIdSaatIni,
            gambar = gambarSaatIni,
            onDismissRequest = { showInputDialog = false },
            viewModel
        )
    }

    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(TempatApi.getTempatUrl(tempat.image_id))
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-100).dp),
            contentScale = ContentScale.Crop,
            contentDescription = tempat.nama_tempat
        )
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
                        Text(
                            text = tempat.nama_tempat,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            arrayOf(1, 2, 3, 4, 5).map {
                                RatingItem(
                                    index = it,
                                    rating = tempat.rating
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        Text(text = "Entry Fee : ", color = Color.Black)
                        Text(
                            text = tempat.biaya_masuk,
                            color = WarnaUtama,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 26.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 12.dp),
                            text = "Photos",
                            color = Color.Black
                        )
                        LazyRow {
                            items(data) { photo ->
                                if (status == ApiStatus.LOADING) {
                                    LoadingAnimation()
                                } else {
                                    SubcomposeAsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(TempatApi.getPhotosUrl(photo.photoUrl))
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(end = 18.dp)
                                            .clickable {
                                                idSaatIni = photo.id
                                                tempatIdSaatIni = photo.tempat_id
                                                gambarSaatIni =
                                                    TempatApi.getPhotosUrl(photo.photoUrl)
                                                showInputDialog = true
                                            }
                                            .clip(RoundedCornerShape(17.dp))
                                            .width(110.dp)
                                            .height(88.dp)
                                    )
                                }
                            }

                            item {
                                Image(
                                    painter = painterResource(id = R.drawable.tambah),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(end = 18.dp)
                                        .clip(RoundedCornerShape(17.dp))
                                        .clickable {
                                            val option = CropImageContractOptions(
                                                null, CropImageOptions(
                                                    imageSourceIncludeGallery = false,
                                                    imageSourceIncludeCamera = true,
                                                    fixAspectRatio = true
                                                )
                                            )
                                            launcher.launch(option)
                                        }
                                        .width(110.dp)
                                        .height(88.dp)
                                )
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
                                text = tempat.alamat ?: "",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 5.dp),
                                color = AbuGelap,
                            )
                            Text(
                                text = tempat.kota,
                                fontSize = 14.sp,
                                color = AbuGelap,
                                fontWeight = FontWeight.Medium
                            )
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
                            .fillMaxWidth()
                            .border(1.dp, Color.Black, RoundedCornerShape(2.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 6.dp),
                            text = "Notes",
                            color = Color.Black
                        )
                        Text(
                            text = tempat.catatan ?: "",
                            fontSize = 14.sp,
                            color = AbuGelap,
                        )
                    }
                }
            }
        }
    }
}

private fun getCroppedImage(
    resolver: ContentResolver,
    result: CropImageView.CropResult
): Bitmap? {
    if (!result.isSuccessful) {
        Log.e("IMAGE", "Error: ${result.error}")
        return null
    }

    val uri = result.uriContent ?: return null

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images.Media.getBitmap(resolver, uri)
    } else {
        val source = ImageDecoder.createSource(resolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}