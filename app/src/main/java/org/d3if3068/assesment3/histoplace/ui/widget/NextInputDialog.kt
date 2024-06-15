package org.d3if3068.assesment3.histoplace.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.d3if3068.assesment3.histoplace.R

@Composable
fun NextInputDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String) -> Unit
) {
    var alamat by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {

                    OutlinedTextField(
                        value = alamat,
                        onValueChange = { alamat = it },
                        label = { Text(text = stringResource(R.string.alamat)) },
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
                        value = catatan,
                        onValueChange = { catatan = it },
                        label = { Text(text = stringResource(R.string.catatan)) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
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
                                    alamat,
                                    catatan
                                )
                            },
                            enabled = alamat.isNotEmpty() && catatan.isNotEmpty(),
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