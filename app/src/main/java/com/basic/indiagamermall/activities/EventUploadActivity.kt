package com.basic.indiagamermall.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.basic.indiagamermall.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.HashMap

class EventUploadActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var uploadImg: ImageView
    lateinit var edtCategory: EditText
    lateinit var btnUpload: Button
    lateinit var categoryName: String
    lateinit var imgUploadUrl: Uri
    lateinit var progressbar: ProgressBar
    lateinit var downloadUrl: String
    lateinit var dbFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_upload)

        initMain()

        initFirebase()
    }

    private fun initMain(){
        uploadImg = findViewById(R.id.img_upload_event)
        btnUpload = findViewById(R.id.btn_upload_event)
        edtCategory = findViewById(R.id.edt_category_event)
        progressbar = findViewById(R.id.progressbar_event)

        uploadImg.setOnClickListener(this)
        btnUpload.setOnClickListener(this)
    }

    private fun initFirebase(){
        dbFirestore = FirebaseFirestore.getInstance()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.img_upload_event -> {
                    selectImage()
                }
                R.id.btn_upload_event -> {
                    progressbar.visibility = View.VISIBLE
                    uploadImageFirebase()
                }
                else -> {

                }
            }
        }
    }

    //To select image from gallary using intent and it's result
    private fun selectImage(){
        val iSelect: Intent = Intent()
        iSelect.type = "image/*"
        iSelect.action = Intent.ACTION_GET_CONTENT
        iSelect.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        iSelect.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(iSelect, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            imgUploadUrl = data?.data!!
            uploadImg.setImageURI(imgUploadUrl)
        }
    }

    private fun uploadImageFirebase(){
        val fileName = Date()
        val fStorageRef: StorageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        fStorageRef.putFile(imgUploadUrl)
            .addOnSuccessListener {
                 it.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                     downloadUrl = it.toString()
                     saveEventFirestore()
                 }

                uploadImg.setImageResource(R.drawable.gamer_logo)
                progressbar.visibility = View.GONE
            }.addOnFailureListener {
                progressbar.visibility = View.GONE
                Toast.makeText(applicationContext, "Upload Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun saveEventFirestore(){
        categoryName = edtCategory.text.toString().trim()
        val docName: String = dbFirestore.collection("EventCategories").document().id

        val eventMap = HashMap<String, Any>()
        eventMap.put("Category", categoryName)
        eventMap.put("Key", docName)
        eventMap.put("ImgUrl", downloadUrl)

        dbFirestore.collection("EventCategories").document(docName).set(eventMap)
            .addOnCompleteListener {
                Toast.makeText(applicationContext, "Event Uploaded Successfully", Toast.LENGTH_SHORT).show()
                edtCategory.setText("")
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Upload Failed", Toast.LENGTH_SHORT).show()

            }
    }
}