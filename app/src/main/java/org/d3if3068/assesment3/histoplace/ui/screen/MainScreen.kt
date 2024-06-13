package org.d3if3068.assesment3.histoplace.ui.screen

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3068.assesment3.histoplace.BuildConfig
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.model.MainViewModel
import org.d3if3068.assesment3.histoplace.model.Tempat
import org.d3if3068.assesment3.histoplace.model.User
import org.d3if3068.assesment3.histoplace.network.ApiStatus
import org.d3if3068.assesment3.histoplace.network.TempatApi
import org.d3if3068.assesment3.histoplace.network.UserDataStore
import org.d3if3068.assesment3.histoplace.ui.theme.AbuAbu
import org.d3if3068.assesment3.histoplace.ui.theme.HistoPlaceTheme
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama
import org.d3if3068.assesment3.histoplace.ui.widget.DisplayAlertDialog
import org.d3if3068.assesment3.histoplace.ui.widget.InputDialog
import org.d3if3068.assesment3.histoplace.ui.widget.ProfilDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
//    onNavigateToScreen: (String, String, Int, String, String, String) -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel()
    val status by viewModel.status.collectAsState()
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())

    val errorMessage by viewModel.errorMessage

    var showDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(CropImageContract()) {
        bitmap = getCroppedImage(context.contentResolver, it)
        if (bitmap != null) showInputDialog = true
    }
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
                    ),
                    actions = {
                        IconButton(onClick = {
                            if (user.email.isEmpty()) {
                                CoroutineScope(Dispatchers.IO).launch { signIn(context, dataStore) }
                            } else {
                                showDialog = true
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.account_circle),
                                contentDescription = stringResource(R.string.profil),
                                tint = WarnaUtama,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                IconButton(
                    modifier = Modifier.size(57.dp),
                    onClick = {
                        val option = CropImageContractOptions(
                            null, CropImageOptions(
                                imageSourceIncludeGallery = false,
                                imageSourceIncludeCamera = true,
                                fixAspectRatio = true
                            )
                        )
                        launcher.launch(option)
                    }) {
                    Image(
                        modifier = Modifier.size(43.dp),
                        painter = painterResource(id = R.drawable.fab),
                        contentDescription = "fab"
                    )
                }
            }
        ) { padding ->
            ScreenContent(viewModel,user.email, Modifier.padding(padding)
//                onNavigateToScreen
            )
            if (showDialog) {
                ProfilDialog(
                    user = user,
                    onDismissRequest = { showDialog = false }) {
                    CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStore) }
                    showDialog = false
                }
            }

            if (showInputDialog) {
                InputDialog(
                    bitmap = bitmap,
                    onDismissRequest = {
                        showInputDialog = false
                    }) { namaTempat, biayaMasuk, kota, negara, rating ->
                    viewModel.saveData(user.email, bitmap!!, namaTempat, "Rp.$biayaMasuk", kota, negara, rating)
                    showInputDialog = false
                }
            }

            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                viewModel.clearMessage()
            }
        }
    }


@Composable
fun ScreenContent(
    viewModel: MainViewModel,
    userId: String,
    modifier: Modifier
//    onNavigateToScreen: (String, String, Int, String, String, String) -> Unit
) {
    val data by viewModel.data
    val status by viewModel.status.collectAsState()

    LaunchedEffect(userId) {
        viewModel.retrieveData(userId)
    }

    when (status) {
        ApiStatus.LOADING -> {
            LoadingScreen()
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
                            modifier = Modifier.size(20.dp),
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
                        focusedContainerColor = WarnaUtama,
                        unfocusedContainerColor = WarnaUtama,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                LazyColumn(contentPadding = PaddingValues(bottom = 40.dp)) {
                    items(data) { tempat ->
                        ListItem(tempat = tempat, onDelete = { tempatId ->
                            Log.d("ScreenContent", "Deleting data with ID: $tempatId")
                            viewModel.deleteData(userId, tempatId)
                        })
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
                    onClick = { viewModel.retrieveData(userId) },
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
    onDelete: (String) -> Unit
//    onNavigateToScreen: (String, String, Int, String, String, String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        openDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirmation = {
            onDelete(tempat.id)
            showDialog = false
        }
    )
    Row(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
            .clickable {
//                       onNavigateToScreen(
//                           tempat
//                       )
//                onNavigateToScreen(
//                    TempatApi.getTempatUrl(tempat.image_id),
//                    tempat.nama_tempat,
//                    tempat.rating,
//                    tempat.biaya_masuk,
//                    tempat.kota,
//                    tempat.catatan!!
//                )
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
                    .data(TempatApi.getTempatUrl(tempat.image_id))
                    .crossfade(true)
                    .build(),
                modifier = Modifier.size(130.dp),
                contentDescription = tempat.nama_tempat,
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
                text = tempat.nama_tempat
            )
            Row(
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            ) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarnaUtama,
                    text = tempat.biaya_masuk
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

private suspend fun signIn(context: Context, dataStore: UserDataStore) {
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.API_KEY)
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val credentialManager = CredentialManager.create(context)
        val result = credentialManager.getCredential(context, request)
        handleSignIn(result, dataStore)
    } catch (e: GetCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
    }
}

private suspend fun handleSignIn(result: GetCredentialResponse, dataStore: UserDataStore) {
    val credential = result.credential
    if (credential is CustomCredential &&
        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
    ) {
        try {
            val googleId = GoogleIdTokenCredential.createFrom(credential.data)
            val nama = googleId.displayName ?: ""
            val email = googleId.id
            val photoUrl = googleId.profilePictureUri.toString()
            dataStore.saveData(User(nama, email, photoUrl))
        } catch (e: GoogleIdTokenParsingException) {
            Log.e("SIGN-IN", "Error: ${e.message}")
        }
    } else {
        Log.e("SIGN-IN", "Error: unrecognized custom credential type")
    }
}

private suspend fun signOut(context: Context, dataStore: UserDataStore) {
    try {
        val credentialManager = CredentialManager.create(context)
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        dataStore.saveData(User())
    } catch (e: ClearCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
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

    var uri = result.uriContent ?: return null

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images.Media.getBitmap(resolver, uri)
    } else {
        val source = ImageDecoder.createSource(resolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPrev() {
//    HistoPlaceTheme {
//        MainScreen(
//            onNavigateToScreen = { imageId, namaTempat, rating, biayaMasuk, kota, catatan->
//            },
//            rememberNavController()
//        )
//    }
//}