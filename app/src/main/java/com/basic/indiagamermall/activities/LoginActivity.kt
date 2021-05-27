package com.basic.indiagamermall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.basic.indiagamermall.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.measureTimeMillis

class LoginActivity : AppCompatActivity() {

    lateinit var  edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var email: String
    lateinit var password: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val imgSignIn = findViewById<ImageView>(R.id.imgSignIn)
        val txtSignUp = findViewById<TextView>(R.id.txtSignUP)

        //To initialize all the views...
        initMain()

        imgSignIn.setOnClickListener{

            //To get edit text data user email and password and call firebase auth func
            getEdtData()

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
         auth = Firebase.auth
    }

    fun getEdtData(){
        if(edtEmail.text != null && edtPassword.text != null){
            email = edtEmail.text.toString().trim()
            password = edtPassword.text.toString().trim()
            if(!email.equals("") && !password.equals(""))
                firebaseAuth()
            else
                Toast.makeText(applicationContext, "Login Details Missing", Toast.LENGTH_SHORT).show()

        } else{
            Toast.makeText(applicationContext, "Login Details Missing", Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(applicationContext, ""+ currentUser.displayName, Toast.LENGTH_SHORT).show()
        }
        if(currentUser != null){
            //reload();
            startMainActivity()
        }
    }

    fun firebaseAuth(){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Companion.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        Toast.makeText(applicationContext, "Login Successfully" , Toast.LENGTH_SHORT).show()
                    }
                    //updateUI(user)
                    startMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Companion.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                   // updateUI(null)
                }
            }
    }

    fun startMainActivity(){
        val iMainActivity = Intent(this, MainActivity :: class.java)
        finish()
        startActivity(iMainActivity)
    }

    companion object {
        const val TAG = "LoginActivity"
    }

}