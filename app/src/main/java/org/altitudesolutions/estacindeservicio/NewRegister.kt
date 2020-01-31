package org.altitudesolutions.estacindeservicio

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.create_register.*
import kotlinx.android.synthetic.main.item_register.*
import org.altitudesolutions.estacindeservicio.REST.RetrofitClient
import org.altitudesolutions.estacindeservicio.models.CreateResponse
import org.altitudesolutions.estacindeservicio.models.GetVehiclesResponse
import org.altitudesolutions.estacindeservicio.models.Vehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NewRegister: AppCompatActivity() {
    private var token: String = ""
    private var userName: String = ""
    private var vehicleArray = mutableListOf<Vehicle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.create_register)
        }else{
            super.onCreate(savedInstanceState)
            setContentView(R.layout.create_register_landscape)
        }

        val bundle: Bundle? = intent.extras
        val userName = bundle?.getString("userName")
//        val realName = bundle?.getString("realName")
        val token = bundle?.getString("token")

        // Set authentication data
        this.token = token!!
        this.userName = userName!!

        fuelVolume.addTextChangedListener {
            var valueToShow: Double
            if(fuelProduct.selectedItem.toString() == "Gasolina"){
                // 3.74
                if(fuelVolume.text.toString() == ""){
                    valueToShow = 0.0
                }else{
                    valueToShow = fuelVolume.text.toString().toDouble() * 3.74
                }
            }else{
                // 3.72
                if(fuelVolume.text.toString() == ""){
                    valueToShow = 0.0
                }else{
                   valueToShow = fuelVolume.text.toString().toDouble() * 3.72
                }
            }
            calculatedCost.setText( valueToShow.toString() )
        }

        fuelProduct.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var times: Double
                if(fuelProduct.selectedItem.toString() == "Gasolina"){
                    times = 3.74
                } else {
                    times = 3.72
                }
                if(fuelVolume.text.toString() == ""){
                    calculatedCost.setText( "0.0 " )
                }else{
                    times *= fuelVolume.text.toString().toFloat()
                    calculatedCost.setText( times.toString() )
                }
            }

        }

        saveButton.setOnClickListener {
            saveButton.isEnabled = false

            if(vehicleNumber.text.isEmpty()){
                vehicleNumber.error = "El vehículo es necesario"
                vehicleNumber.requestFocus()
                saveButton.isEnabled = true
                return@setOnClickListener
            }

            if(vehicleKilometer.text.isEmpty() && vehicleNumber.text.toString() != "Bidon"){
                vehicleKilometer.error = "El kilometraje es necesario"
                vehicleKilometer.requestFocus()
                saveButton.isEnabled = true
                return@setOnClickListener
            }

            if(vehicleNumber.text.toString() == "Bidon") {
                vehicleKilometer.setText("0.0")
            }

            if(fuelVolume.text.isEmpty()){
                fuelVolume.error = "El volumen es necesario"
                fuelVolume.requestFocus()
                saveButton.isEnabled = true
                return@setOnClickListener
            }

            val currentVehicle = vehicleArray.find{
                    v -> v.movil == vehicleNumber.text.toString()
            }

            if (currentVehicle != null) {
                if(currentVehicle.capacidadCombustible != 0.0f) {
                    if (currentVehicle.capacidadCombustible < fuelVolume.text.toString().toFloat()) {
                        fuelVolume.error = "El volumen excede la capacidad del vehículo " + currentVehicle.capacidadCombustible.toString() + "L"
                        fuelVolume.requestFocus()
                        saveButton.isEnabled = true
                        return@setOnClickListener
                    }
                }
            }else{
                vehicleNumber.error = "El vehículo no existe"
                vehicleNumber.requestFocus()
                saveButton.isEnabled = true
                return@setOnClickListener
            }

            val date: String = Date().time.toString()
            RetrofitClient.Instance.createRegister(this.token, fuelProduct.selectedItem.toString(), fuelVolume.text.toString().toFloat(), calculatedCost.text.toString().toFloat(), date.toLong(), vehicleKilometer.text.toString().toFloat(), vehicleNumber.text.toString(), this.userName)
                .enqueue(object: Callback<CreateResponse> {
                    override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        saveButton.isEnabled = true
                    }

                    override fun onResponse(
                        call: Call<CreateResponse>,
                        response: Response<CreateResponse>
                    ) {
                        saveButton.isEnabled = true
                        if(!response.body()?.message.isNullOrBlank()){
                            Toast.makeText(this@NewRegister, response.body()?.message.toString(), Toast.LENGTH_LONG).show()
                        }
                        if(response.body()?.saved == true){
                            vehicleNumber.text.clear()
                            vehicleKilometer.text.clear()
                            fuelVolume.text.clear()
                            this@NewRegister.finish()
                        }else{
                            if(!response.body()?.errKm.isNullOrBlank()){
                                this@NewRegister.vehicleKilometer.error = response.body()?.errKm.toString()
                            }
                        }
                    }
                })
        }

        // load vehicle list
        loadVehicles()
    }

    private fun loadVehicles () {
        vehicleArray.clear()

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
//                        Toast.makeText(applicationContext, "Vehículos " + response.body()?.count.toString() + " descargados", Toast.LENGTH_LONG).show()
                        val vehicleList: List<Vehicle>? = response.body()?.vehiculos;
                        if (vehicleList != null) {
                            var suggestions = mutableListOf<String>()
                            for (vehicle in vehicleList){
                                vehicleArray.add(vehicle)
                                suggestions.add(vehicle.movil)
                            }

                            val adapter = ArrayAdapter<String>(
                                this@NewRegister,
                                android.R.layout.simple_list_item_1,
                                suggestions
                            )

                            vehicleNumber.setAdapter(adapter)
                            vehicleNumber.threshold = 1
                        }
                    }
                }
            })
    }
}