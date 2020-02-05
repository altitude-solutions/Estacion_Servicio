package org.altitudesolutions.estacindeservicio.REST

import org.altitudesolutions.estacindeservicio.models.*
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

    @FormUrlEncoded
    @POST("/ventaCombustible")
    fun createRegister(
        @Header("token") token: String,
        @Field("producto") producto: String,
        @Field("litros") litros: Float,
        @Field("precioTotal") precioTotal: Float,
        @Field("fechaYHora") fechaYHora: Long,
        @Field("kilometraje") kilometraje: Float,
        @Field("movil") movil: String,
        @Field("usuario_id") usuario: String,
        @Field("comentarios") comentarios: String
    ): Call<CreateResponse>

    @GET("ventaCombustible")
    @Headers("Content-Type: application/json")
    fun getVentas(
        @Header("token") token: String
    ): Call<GetVentasResponse>
}