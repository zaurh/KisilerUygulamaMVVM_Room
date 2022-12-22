package com.example.kisileruygulama

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kisileruygulama.entity.Kisiler

@Composable
fun KisiDetaySayfa(gelenKisi: Kisiler){
    val tfKisiAd = remember { mutableStateOf("") }
    val tfKisiTel = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true ){
        tfKisiAd.value = gelenKisi.kisi_ad
        tfKisiTel.value = gelenKisi.kisi_tel
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Kişi Detay") })
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = tfKisiAd.value,
                    onValueChange = {tfKisiAd.value = it},
                    label ={ Text(text = "Kisi ad")}
                )
                TextField(
                    value = tfKisiTel.value,
                    onValueChange = {tfKisiTel.value = it},
                    label ={ Text(text = "Kisi tel")}
                )
                Button(
                    onClick = {
                        val kisi_ad = tfKisiAd.value
                        val kisi_tel = tfKisiTel.value
                        Log.e("Kişi güncelle", "${gelenKisi.kisi_id} - $kisi_ad - $kisi_tel")
                        localFocusManager.clearFocus()
                    },
                    modifier = Modifier.size(250.dp, 50.dp)
                )
                {
                    Text(text = "Güncelle")
                }
            }
        }
    )
}