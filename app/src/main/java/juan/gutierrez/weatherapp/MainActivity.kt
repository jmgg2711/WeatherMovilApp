package juan.gutierrez.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var ciudad: EditText
    lateinit var btnBuscar: Button
    lateinit var tempActual: TextView
    val apiKey = "04a6a728da562771d87c1b4f26ad02d0"
    var city = "Tijuana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ciudad = findViewById(R.id.edTxt_Ciudad)
        tempActual = findViewById(R.id.txtView_Temperatura)
        btnBuscar = findViewById(R.id.btn_buscar)

        btnBuscar.setOnClickListener{
            city = ciudad.text.toString()
            search_weather()
        }
    }

    private fun search_weather(){
        val call = WeatherApiClient.weatherService.getCurrentWeather(city, apiKey)

        call.enqueue(object:Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                Toast.makeText(applicationContext,response.message(), Toast.LENGTH_LONG).show()
                if(response.isSuccessful){
                    val weather = response.body()
                    tempActual.text = "Temperatura: ${weather?.main?.temp}°C \nHumedad: ${weather?.main?.humidity}"
                }else{
                    Log.e("MainActivity", response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"Error!", Toast.LENGTH_LONG).show()
            }
        })
    }
}