package com.basic.indiagamermall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.basic.indiagamermall.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val imgSignUp = findViewById<ImageView>(R.id.imgSignUp)
        val txtSignIn = findViewById<TextView>(R.id.txtSignIn)

        initMain()

        imgSignUp.setOnClickListener{
            getEdtData()
        }

        txtSignIn.setOnClickListener{

            val iLoginActivity = Intent(this, LoginActivity :: class.java)
            finish()
            startActivity(iLoginActivity)
        }
    }

    //To initialize all the view
    fun initMain(){

        auth = Firebase.auth
        edtEmail = findViewById(R.id.edtEmail)
        edtName = findViewById(R.id.edtName)
        edtPassword = findViewById(R.id.edtPassword)
    }

    //To get edit Text data
    fun getEdtData(){
        if(edtName.text != null && edtEmail.text != null && edtPassword.text != null){
            name = edtName.text.toString().trim()
            email = edtEmail.text.toString().trim()
            password = edtPassword.text.toString().trim()
            if(!name.equals("") && !email.equals("") && !password.equals("")){
                firebaseAuth()
            }else{
                Toast.makeText(applicationContext, "Missing Some Details!", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(applicationContext, "Missing Login Details!", Toast.LENGTH_SHORT).show();
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
           startMainActivity()
        }
    }

    fun firebaseAuth(){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Companion.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    var request: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build()
                    user?.updateProfile(request)?.addOnCompleteListener(OnCompleteListener {
                        Log.d(TAG, "User profile updated.");
                    })
                    Toast.makeText(applicationContext, "" + user, Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                    startMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Companion.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    //To call main activity intent
    fun startMainActivity(){
        val iMainActivity = Intent(this, MainActivity :: class.java)
        finish()
        startActivity(iMainActivity)
    }

    companion object {
        const val TAG = "Registration"
    }
}