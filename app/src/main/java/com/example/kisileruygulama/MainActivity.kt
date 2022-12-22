package com.example.kisileruygulama

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kisileruygulama.entity.Kisiler
import com.example.kisileruygulama.ui.theme.KisileruygulamaTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KisileruygulamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SayfaGecisleri()
                }
            }
        }
    }
}
@Composable
fun SayfaGecisleri(){
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa"){
        composable("anasayfa"){
            Anasayfa(navController = navController )
        }
        composable("kisi_kayit_sayfa"){
            KisiKayitSayfa()
        }
        composable("kisi_detay_sayfa/{kisi}", arguments = listOf(
            navArgument("kisi"){
                type = NavType.StringType
            }
        )){
            val json = it.arguments?.getString("kisi")
            val nesne = Gson().fromJson(json, Kisiler::class.java)
            KisiDetaySayfa(nesne)
        }
    }
}

@Composable
fun Anasayfa(navController: NavController){
    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    val kisilerListesi = remember { mutableStateListOf<Kisiler>() }

    LaunchedEffect(key1 = true){
        val k1 = Kisiler(1,"Zaur","514348813")
        val k2 = Kisiler(2,"Isa","513341923")
        kisilerListesi.add(k1)
        kisilerListesi.add(k2)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (aramaYapiliyorMu.value){
                       TextField(
                           value = tf.value,
                           onValueChange = {
                               tf.value = it
                               Log.e("Kişi arama", "$it")

                                           },
                           textStyle = TextStyle(fontSize = 15.sp),
                           singleLine = true,
                           label = {Text(text = "Ara", color = Color.White)},
                           colors = TextFieldDefaults.textFieldColors(
                               backgroundColor = Color.Transparent,
                               focusedLabelColor = Color.White,
                               focusedIndicatorColor = Color.White,
                               unfocusedLabelColor = Color.White,
                               unfocusedIndicatorColor = Color.White,
                              cursorColor = Color.White,
                           )
                           )
                    }else{
                        Text(text = "Kisiler")
                    }
                },
                actions = {
                    if (aramaYapiliyorMu.value){
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = false
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.kapat_resim),
                                contentDescription = "", tint = Color.White)

                        }
                    }else{
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = true

                        }) {
                            Icon(painter = painterResource(id = R.drawable.arama_resim),
                                contentDescription = "", tint = Color.White)

                        }
                    }

                }
            )
        },
        content = {
                  LazyColumn{
                      items(
                          count = kisilerListesi.count(),
                          itemContent = {
                              val kisi = kisilerListesi[it]
                              Card(modifier = Modifier
                                  .padding(all = 5.dp)
                                  .fillMaxWidth()) {
                                  Row(modifier = Modifier.clickable {
                                      val kisiJson = Gson().toJson(kisi)
                                      navController.navigate("kisi_detay_sayfa/${kisiJson}")
                                  }) {
                                      Row(
                                          modifier = Modifier
                                              .padding(10.dp)
                                              .fillMaxWidth(),
                                          verticalAlignment = Alignment.CenterVertically,
                                          horizontalArrangement = Arrangement.SpaceBetween
                                      ) {
                                          Text(text = "${kisi.kisi_ad} - ${kisi.kisi_tel}", fontSize = 18.sp)

                                          IconButton(onClick = { /*TODO*/ }) {
                                              Icon(painter = painterResource(id = R.drawable.sil_resim),
                                                  contentDescription = "", tint = Color.Gray)
                                          }
                                      }
                                  }
                              }

                          }
                      )
                  }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate("kisi_kayit_sayfa")
                },
                backgroundColor = colorResource(id = R.color.teal_200),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ekle_resim),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KisileruygulamaTheme {
        SayfaGecisleri()
    }
}