package com.example.myweather

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweather.data.WeatherModel
import com.example.myweather.screens.MainScreen
import com.example.myweather.ui.theme.MyWeatherTheme
import org.json.JSONObject


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MyWeatherTheme {
                Image(
                    painter = painterResource(id = R.drawable.weath),
                    contentDescription = "im",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f),  // прозрачноть
                    contentScale = ContentScale.FillBounds,          // рястянуть фото
                )
                Column {
                    getResult("Pavlodar", this@MainActivity)
                    //MainScreen(item)
                    //TabLayout()

                }
            }
        }
    }
}
fun getResult(city:String,context: Context){
    val url = "http://api.weatherapi.com/v1/current.json?" +
            "key=$API_KEY&" +
            "q=$city"+
            "&days="+
            "3"+
            "&aqi=no"

    val queue = Volley.newRequestQueue( context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
                result->
            parseWeatherData(result)
            // val obj = JSONObject(response)
            //state.value = obj.getJSONObject("current").getString("temp_c")
            Log.d("MyLog","Response: $result")
        },
        {
                error->
            Log.d("MyLog","Error: $error")
        }
    )
    queue.add(stringRequest)
}




const val API_KEY = "ff29e17572224e919a673653222607"


fun parseWeatherData(result:String) {
    val mainObject = JSONObject(result)
    val item = WeatherModel(
        mainObject.getJSONObject("location").getString("name"),
        mainObject.getJSONObject("current").getString("last_updated"),
        mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
        mainObject.getJSONObject("current").getString("temp_c"),
        "",
        "",
        "",
        "",
        ""
    )


}

