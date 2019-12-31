package org.altitudesolutions.estacindeservicio.models


//{
//    "user": {
//    "nombreUsuario": "root",
//    "nombreReal": "Javier Mauricio Contreras Guzmán",
//    "permisos": [
//
//    ],
//    "empresa": "Altitude Solutions S.R.L.",
//    "correo": null
//},
//    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7Im5vbWJyZVVzdWFyaW8iOiJyb290Iiwibm9tYnJlUmVhbCI6IkphdmllciBNYXVyaWNpbyBDb250cmVyYXMgR3V6bcOhbiIsInBlcm1pc29zIjpbImVzX2xlZXIiLCJlc19lc2NyaWJpciIsImVzX2JvcnJhciIsImVzX21vZGlmaWNhciIsIm9yX2xlZXIiLCJvcl9lc2NyaWJpciIsIm9yX2JvcnJhciIsIm9yX21vZGlmaWNhciIsInVfbGVlciIsInVfZXNjcmliaXIiLCJ1X2JvcnJhciIsInVfbW9kaWZpY2FyIiwicnVfbGVlciIsInJ1X2VzY3JpYmlyIiwicnVfYm9ycmFyIiwicnVfbW9kaWZpY2FyIiwicF9sZWVyIiwicF9lc2NyaWJpciIsInBfYm9ycmFyIiwicF9tb2RpZmljYXIiLCJ2ZV9sZWVyIiwidmVfZXNjcmliaXIiLCJ2ZV9ib3JyYXIiLCJ2ZV9tb2RpZmljYXIiLCJpb19sZWVyIiwiaW9fZXNjcmliaXIiLCJpb19ib3JyYXIiLCJpb19tb2RpZmljYXIiLCJmaW5fbGVlciIsImZpbl9lc2NyaWJpciIsImZpbl9ib3JyYXIiLCJmaW5fbW9kaWZpY2FyIiwicHJvX2xlZXIiLCJwcm9fZXNjcmliaXIiLCJwcm9fYm9ycmFyIiwicHJvX21vZGlmaWNhciJdLCJlbXByZXNhIjoiQWx0aXR1ZGUgU29sdXRpb25zIFMuUi5MLiIsImNvcnJlbyI6bnVsbH0sImlhdCI6MTU3NzY3ODMzNywiZXhwIjoxNTc3NzY0NzM3fQ.sC5b5znDDklG7LgNdorroKHI6m6Q6mu3yvtDhc1ejs4"
//}



data class User(
    val nombreUsuario: String,
    val nombreReal: String,
    val empresa: String,
    val permisos: List<String>,
    val correo: String
)