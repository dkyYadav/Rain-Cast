/*
package com.example.raincast.presentation.view

import androidx.compose.foundation.Image
import com.example.raincast.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun Home() {
    var showSearchField by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.weathercolorDARK)),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(top = 5.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = " $city ",
                                fontSize = 24.sp,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    showSearchField = !showSearchField
                                }
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.weathercolorDARK),
                        titleContentColor = colorResource(id = R.color.white)
                    )
                )

                if (showSearchField) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.weathercolorDARK)),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            label = {
                                Text("Enter city", color = Color.White)
                            },
                            singleLine = true,
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (city.isNotBlank()) {
                                        println("Searching for: $city")
                                        showSearchField = false
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Submit",
                                        tint = Color.White
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(
                                    color = colorResource(id = R.color.weathercolorDARK),
                                    shape = MaterialTheme.shapes.medium
                                ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White,
                                unfocusedBorderColor = Color.White,
                                focusedBorderColor = Color.White,
                                cursorColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                focusedLabelColor = Color.White
                            )
                        )
                    }
                }

            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(painter = painterResource(R.drawable.weatherbg),
                contentDescription = "bgi",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "25 °C",
                        color = Color.White,
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Text(
                            text = "Max!",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
*/
/*
package com.example.raincast.presentation.view

import com.example.raincast.R
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun Home() {

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.weathercolorDARK)
            ),
        topBar = {
            TopAppBar(
                title = {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .padding(top = 5.dp,)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Noida",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.weight(1f)) // icon is end of row
                        IconButton(
                            onClick = {}
                        ){
                            Icon(Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.weathercolorDARK),
                    titleContentColor = colorResource(id = R.color.white))
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(
                    color = colorResource(id = R.color.weathercolorDARK)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = " 25 °C",
                        color = Color.White,
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        Text(text = "hii",
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}
*/

