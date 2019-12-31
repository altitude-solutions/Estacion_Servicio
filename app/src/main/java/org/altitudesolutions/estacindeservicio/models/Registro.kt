package org.altitudesolutions.estacindeservicio.models

data class Registro(
    val movil: Int,
    val kilometraje: Float,
    val producto: String,
    val litros: Float,
    val precioTotal: Float,
    val fechaYHora: Long,
    val usuario: String
)