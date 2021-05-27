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

class ProductUploadActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var category: String
    lateinit var uploadImg: ImageView
    lateinit var edtTitle: EditText
    lateinit var edtDescription: EditText
    lateinit var edtPrice: EditText
    lateinit var btnUpload: Button
    lateinit var productTitle: String
    lateinit var productDescrip: String
    lateinit var productPrice: String
    lateinit var categoryName: String
    lateinit var productKey: String
    lateinit var imgUploadUrl: Uri
    lateinit var progressbar: ProgressBar
    lateinit var downloadUrl: String
    lateinit var dbFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_upload)

        initMain()

        initFirebase()
    }

    //To initialize all the views
    private fun initMain(){
        category = intent.getStringExtra("categoryName")!!
        //Toast.makeText(applicationContext, category, Toast.LENGTH_SHORT).show()
        uploadImg = findViewById(R.id.img_upload_product)
        btnUpload = findViewById(R.id.btn_upload_product)
        edtTitle = findViewById(R.id.edt_title_product_upload)
        edtDescription = findViewById(R.id.edt_descrip_product_upload)
        edtPrice = findViewById(R.id.edt_price_product_upload)
        progressbar = findViewById(R.id.progressbar_product)

        uploadImg.setOnClickListener(this)
        btnUpload.setOnClickListener(this)

    }

    private fun initFirebase(){
        dbFirestore = FirebaseFirestore.getInstance()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_upload_product -> {
                selectImage()
            }
            R.id.btn_upload_product -> {
                progressbar.visibility = View.VISIBLE
                uploadImageFirebase()
            } else -> {

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

    //To upload image on firebase cloud storage
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

    //To upload data on firestore
    fun saveEventFirestore(){
        productTitle = edtTitle.text.toString().trim()
        productDescrip = edtDescription.text.toString().trim()
        productPrice = edtPrice.text.toString().trim()

        val docName: String = dbFirestore.collection("Products_CategoryWise: "+ category).document().id

        val productMap = HashMap<String, Any>()
        productMap.put("productTitle", productTitle)
        productMap.put("productDescription", productDescrip)
        productMap.put("productPrice", productPrice)
        productMap.put("productCategory", category)
        productMap.put("productKey", docName)
        productMap.put("ImgUrl", downloadUrl)

        dbFirestore.collection("Products_CategoryWise: "+ category).document(docName).set(productMap)
            .addOnCompleteListener {
                Toast.makeText(applicationContext, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show()
                edtTitle.setText("")
                edtDescription.setText("")
                edtPrice.setText("")
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Upload Failed", Toast.LENGTH_SHORT).show()

            }
    }
}