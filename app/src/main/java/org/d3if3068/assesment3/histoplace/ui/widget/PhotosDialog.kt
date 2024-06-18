package org.d3if3068.assesment3.histoplace.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.model.MainViewModel
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama

@Composable
fun PhotosDialog(
    id: String,
    tempatId: String,
    gambar: String,
    onDismissRequest: () -> Unit,
    viewModel: MainViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

        TrashAllertPhotos(
            openDialog = showDialog,
            onDismissRequest = { showDialog = false }) {
            viewModel.deletePhoto(id, tempatId)
            onDismissRequest()
        }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White)
                .height(340.dp)
                .width(330.dp)
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(gambar)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .border(2.dp, WarnaUtama)
                        .height(250.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "ini"
                )
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "",
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                colors = ButtonColors(
                    containerColor = WarnaUtama,
                    contentColor = WarnaUtama,
                    disabledContentColor = WarnaUtama,
                    disabledContainerColor = WarnaUtama
                ),
                shape = RoundedCornerShape(17.dp),
                modifier = Modifier
                    .width(210.dp)
                    .height(50.dp),
                onClick = { onDismissRequest() }) {
                Text(
                    text = "Tutup",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}