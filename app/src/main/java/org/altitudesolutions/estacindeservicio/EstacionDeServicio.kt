package org.altitudesolutions.estacindeservicio

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.estacion_de_servicio.*
import org.altitudesolutions.estacindeservicio.Adapters.RegisterAdapter
import org.altitudesolutions.estacindeservicio.REST.RetrofitClient
import org.altitudesolutions.estacindeservicio.models.GetVehiclesResponse
import org.altitudesolutions.estacindeservicio.models.GetVentasResponse
import org.altitudesolutions.estacindeservicio.models.Register
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
//        val realName = bundle?.getString("realName")
        val token = bundle?.getString("token")

        // Set authentication data
        this.token = token!!
        this.userName = userName!!

        // Load App data
        loadVehicles()
        loadRegisters()
    }

    private fun loadVehicles () {
        RetrofitClient.Instance.getVehiclesList(this.token)
            .enqueue(object: Callback<GetVehiclesResponse> {
                override fun onFailure(call: Call<GetVehiclesResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetVehiclesResponse>,
                    response: Response<GetVehiclesResponse>
                ) {
                    if(response.code() != 200) {
                        Toast.makeText(
                            applicationContext,
                            "Error cargando la base de datos de vehículos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(applicationContext, "Vehículos " + response.body()?.count.toString() + " descargados", Toast.LENGTH_LONG).show()
                        val vehicleList: List<Vehicle>? = response.body()?.vehiculos;
                        if (vehicleList != null) {
                            for (vehicle in vehicleList){
                                Log.i("Vehiculos", vehicle.movil + " has " + vehicle.capacidadCombustible.toString() + " L capacity")
                            }
                        }
                    }
                }
            })
    }

    private fun loadRegisters() {
        RetrofitClient.Instance.getVentas(this.token)
            .enqueue(object: Callback<GetVentasResponse>{
                override fun onFailure(call: Call<GetVentasResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetVentasResponse>,
                    response: Response<GetVentasResponse>
                ) {
                    if(response.code() != 200){
                        Toast.makeText(
                            applicationContext,
                            "Error cargando los registros de hoy",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        val registers: List<Register>? = response.body()?.ventas
                        val layoutManager = LinearLayoutManager(this@EstacionDeServicio)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL

                        if (registers != null) {
                            for(regi in registers){
                                Log.i("Estacion", regi.movil + "\t" + regi.producto)
                            }
                        }
                        // Set layout to vertical linear layour
                        this@EstacionDeServicio.tableView.layoutManager = layoutManager

                        // Set adapter to RegisterAdapter type
                        val adapter = RegisterAdapter(this@EstacionDeServicio, registers!!)
                        this@EstacionDeServicio.tableView.adapter = adapter
                    }
                }
            })
    }
}