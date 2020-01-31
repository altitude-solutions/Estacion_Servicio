package org.altitudesolutions.estacindeservicio.models

data class CreateResponse (
    val message: String,
    val saved: Boolean,
    val errKm: String
)