package org.altitudesolutions.estacindeservicio.models

data class Register(
    val movil: String,
    val kilometraje: Float,
    val producto: String,
    val litros: Float,
    val precioTotal: Float,
    val fechaYHora: Long,
    val usuario_id: String
)