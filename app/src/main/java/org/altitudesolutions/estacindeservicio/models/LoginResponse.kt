package org.altitudesolutions.estacindeservicio.models

data class LoginResponse(
    val token: String,
    val user: User
)