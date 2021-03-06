package org.altitudesolutions.estacindeservicio

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.login.*
import org.altitudesolutions.estacindeservicio.REST.RetrofitClient
import org.altitudesolutions.estacindeservicio.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.login)
        }else{
            super.onCreate(savedInstanceState)
            setContentView(R.layout.login_landscape)
        }


        loginButton.setOnClickListener {
            loginButton.isEnabled = false
            val username: String = username_text.text.toString()
            val password: String = password_text.text.toString()

            if(username.isEmpty()){
                username_text.error = "El nombre de usuario es necesario"
                username_text.requestFocus()
                loginButton.isEnabled = true
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                password_text.error = "La contraseña es necesaria"
                password_text.requestFocus()
                loginButton.isEnabled = true
                return@setOnClickListener
            }


            RetrofitClient.Instance.login(username, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        loginButton.isEnabled = true
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        loginButton.isEnabled = true
                        if(response.code() != 200){
                            Toast.makeText(applicationContext, "Usuario o contrseña incorrectos", Toast.LENGTH_SHORT).show()
                        }else{
//                            Toast.makeText(applicationContext, "Bienvenido " + response.body()?.user?.nombreReal, Toast.LENGTH_LONG).show()
                            val intent = Intent (this@Login, EstacionDeServicio::class.java)
                            intent.putExtra("realName", response.body()?.user?.nombreReal )
                            intent.putExtra("token", response.body()?.token )
                            intent.putExtra("userName", response.body()?.user?.nombreUsuario )
                            username_text.requestFocus()
                            username_text.text.clear()
                            password_text.text.clear()
                            startActivity(intent)
                        }
                    }
                })
        }
    }
}
