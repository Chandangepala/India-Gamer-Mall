package com.basic.indiagamermall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basic.indiagamermall.R

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val imgSignUp = findViewById<ImageView>(R.id.imgSignUp)
        val txtSignIn = findViewById<TextView>(R.id.txtSignIn)

        imgSignUp.setOnClickListener{
            val iMainActivity = Intent(this, MainActivity :: class.java)
            startActivity(iMainActivity)
        }

        txtSignIn.setOnClickListener{
            val iLoginActivity = Intent(this, LoginActivity :: class.java)
            startActivity(iLoginActivity)
        }
    }
}