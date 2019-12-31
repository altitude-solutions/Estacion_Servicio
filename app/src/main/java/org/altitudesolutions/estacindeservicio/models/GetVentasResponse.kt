package org.altitudesolutions.estacindeservicio.models

data class GetVentasResponse(
    val ventas: List<Register>,
    val count: Int
)