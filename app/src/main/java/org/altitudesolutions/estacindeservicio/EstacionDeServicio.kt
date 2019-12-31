package org.altitudesolutions.estacindeservicio

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.altitudesolutions.estacindeservicio.REST.RetrofitClient
import org.altitudesolutions.estacindeservicio.models.GetVehiclesResponse
import org.altitudesolutions.estacindeservicio.models.Vehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EstacionDeServicio: AppCompatActivity() {
    private var token: String = ""
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.estacion_de_servicio)

        val bundle: Bundle? = intent.extras
        val userName = bundle?.getString("userName")
        val realName = bundle?.getString("realName")
        val token = bundle?.getString("token")

//        Log.i("Main", token!!)
//        Log.i("Main", realName!!)
//        Log.i("Main", userName!!)

        this.token = token!!
        this.userName = userName!!


//        Toast.makeText(this, realName, Toast.LENGTH_SHORT).show()

        loadVehicles()
    }

    private fun loadVehicles () {
        RetrofitClient.Instance.getVehiclesList(this.token)
            .enqueue(object: Callback<GetVehiclesResponse> {
                override fun onFailure(call: Call<GetVehiclesResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error downloading vehicle list", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<GetVehiclesResponse>,
                    response: Response<GetVehiclesResponse>
                ) {
                    Toast.makeText(applicationContext, "Vehículos " + response.body()?.count.toString() + " descargados", Toast.LENGTH_LONG).show()
                    val vehicleList: List<Vehicle>? = response.body()?.vehiculos;
                    if (vehicleList != null) {
                        for (vehicle in vehicleList){
                            Log.i("Vehiculos", vehicle.movil + " has " + vehicle.capacidadCombustible.toString() + " L capacity")
                        }
                    }
                }
            })
    }
}