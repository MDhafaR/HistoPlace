package org.d3if3068.assesment3.histoplace.ui.widget

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.ui.theme.HistoPlaceTheme
import org.d3if3068.assesment3.histoplace.ui.theme.Orange

@Composable
fun InputDialog(
    bitmap: Bitmap?,
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String, String, String, Int) -> Unit
) {
    var namaTempat by remember { mutableStateOf("") }
    var biayaMasuk by remember { mutableStateOf("") }
    var kota by remember { mutableStateOf("") }
    var negara by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(0) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .height(600.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )

                }

                item {

                    OutlinedTextField(
                        value = namaTempat,
                        onValueChange = { namaTempat = it },
                        label = { Text(text = stringResource(R.string.nama_tempat)) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {

                    OutlinedTextField(
                        value = biayaMasuk,
                        onValueChange = { biayaMasuk = if (it == "0") "Gratis" else it },
                        label = { Text(text = stringResource(R.string.biaya_masuk)) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {

                    OutlinedTextField(
                        value = kota,
                        onValueChange = { kota = it },
                        label = { Text(text = stringResource(R.string.kota)) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {

                    OutlinedTextField(
                        value = negara,
                        onValueChange = { negara = it },
                        label = { Text(text = stringResource(R.string.negara)) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        (1..5).forEach { number ->
                            val tint = if (number <= rating) Orange else Color.Gray
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = null, // Set appropriate content description
                                modifier = Modifier
                                    .size(25.dp) // Adjust size as needed
                                    .clickable {
                                        rating = number // Update rating when clicked
                                    },
                                tint = tint
                            )
                        }
                    }



                }

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = stringResource(R.string.batal))
                        }
                        OutlinedButton(
                            onClick = {
                                onConfirmation(
                                    namaTempat,
                                    biayaMasuk,
                                    kota,
                                    negara,
                                    rating,
                                )
                            },
                            enabled = namaTempat.isNotEmpty() && biayaMasuk.isNotEmpty() && kota.isNotEmpty() &&
                                    negara.isNotEmpty() &&
                                    rating > 0,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = stringResource(R.string.simpan))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddDialogPreview() {
    HistoPlaceTheme {
        InputDialog(
            bitmap = null,
            onDismissRequest = { },
            onConfirmation = { _, _, _, _, _,-> }
        )
    }
}