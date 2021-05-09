package com.basic.indiagamermall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.basic.indiagamermall.R

class LoginActivity : AppCompatActivity() {

    lateinit var  edtEmail: EditText
    lateinit var edtPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val imgSignIn = findViewById<ImageView>(R.id.imgSignIn)
        val txtSignUp = findViewById<TextView>(R.id.txtSignUP)

        //To initialize all the views...
        initMain()

        imgSignIn.setOnClickListener{

            if(edtEmail.text != null && edtPassword.text != null){
                if(edtEmail.text.toString().equals("admin") && edtPassword.text.toString().equals("admin123")){
                    val iMainActivity = Intent(this, MainActivity :: class.java)
                    finish()
                    startActivity(iMainActivity)
                }else{
                    Toast.makeText(applicationContext, "Wrong login details, Try again!", Toast.LENGTH_SHORT).show()
                }
            }

        }

        txtSignUp.setOnClickListener{
            val iLoginActivity = Intent(this, RegistrationActivity :: class.java)
            finish()
            startActivity(iLoginActivity)
        }
    }

    //To initialize all the views in the layout
    fun initMain(){
         edtEmail = findViewById<EditText>(R.id.edtEmail)
         edtPassword = findViewById<EditText>(R.id.edtPassword)
    }
}