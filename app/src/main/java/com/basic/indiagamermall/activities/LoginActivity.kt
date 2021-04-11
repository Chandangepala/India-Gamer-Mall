package com.basic.indiagamermall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.basic.indiagamermall.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val imgSignIn = findViewById<ImageView>(R.id.imgSignIn)
        val txtSignUp = findViewById<TextView>(R.id.txtSignUP)

        imgSignIn.setOnClickListener{
            val iMainActivity = Intent(this, MainActivity :: class.java)
            startActivity(iMainActivity)
        }

        txtSignUp.setOnClickListener{
            val iLoginActivity = Intent(this, RegistrationActivity :: class.java)
            startActivity(iLoginActivity)
        }
    }
}