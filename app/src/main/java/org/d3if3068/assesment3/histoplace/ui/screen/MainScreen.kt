package org.d3if3068.assesment3.histoplace.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3068.assesment3.histoplace.R
import org.d3if3068.assesment3.histoplace.ui.theme.AbuAbu
import org.d3if3068.assesment3.histoplace.ui.theme.HistoPlaceTheme
import org.d3if3068.assesment3.histoplace.ui.theme.WarnaUtama

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
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
                    contentDescription = "fab")
            }
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
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
        LazyColumn(){
            item() {
                ListItem()
                ListItem()
                ListItem()
                ListItem()
                ListItem()
                ListItem()
                ListItem()
                Column(
                    modifier = Modifier.padding(bottom = 40.dp)
                ){}
            }
        }
    }
}

@Composable
fun ListItem() {
    Row(
        modifier = Modifier.padding(bottom = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(17.dp))
                .width(130.dp)
                .height(110.dp)
        ) {
            Image(
                modifier = Modifier.size(130.dp),
                painter = painterResource(id = R.drawable.contoh),
                contentDescription = "contoh"
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
                    text = "4/5"
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 8.dp)
        ) {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                text = "Kuretakeso Hott")
            Row(
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            ) {
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarnaUtama,
                    text = "Rp.20000")
                Text(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    text = "/ jam")
            }
            Text(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                text = "Bandung, Indonesia")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPrev() {
    HistoPlaceTheme {
        MainScreen()
    }
}