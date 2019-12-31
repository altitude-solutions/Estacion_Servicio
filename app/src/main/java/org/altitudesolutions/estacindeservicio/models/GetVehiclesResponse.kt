package org.altitudesolutions.estacindeservicio.models

data class GetVehiclesResponse(
    val vehiculos: List<Vehicle>,
    val count: Int
)