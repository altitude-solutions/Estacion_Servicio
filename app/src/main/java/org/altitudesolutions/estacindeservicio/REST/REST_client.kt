package org.altitudesolutions.estacindeservicio.REST

import org.altitudesolutions.estacindeservicio.models.GetVehiclesResponse
import org.altitudesolutions.estacindeservicio.models.LoginResponse
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.Header

interface REST_client {

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("nombreUsuario") userName: String,
        @Field("contra") password: String
    ): Call<LoginResponse>

    @GET("/vehi")
    @Headers("Content-Type: application/json")
    fun getVehiclesList (
        @Header("token")  token: String
    ): Call<GetVehiclesResponse>
}