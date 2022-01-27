package com.engresearch.wheatclassification.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.engresearch.wheatclassification.R
import com.engresearch.wheatclassification.utils.Utils
import com.engresearch.wheatclassification.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var wheatImage: File
    private lateinit var textView: TextView
    private lateinit var path: String
    private lateinit var viewModel: MainViewModel
    private val cameraRequest = 1888
    private lateinit var progress : ProgressDialog
    private var pictureImagePath : String =""
    private lateinit var tempImageFile : File



    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = ProgressDialog(this)

        imageView = findViewById<ImageView>(R.id.imageView)
        textView = findViewById<TextView>(R.id.textView)
        var buttonCamera = findViewById<ImageView>(R.id.imageView3)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )


        imageView = findViewById(R.id.imageView)

        buttonCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())

            val root_dir: File?
            root_dir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                getExternalFilesDir(null)
            } else {
                cacheDir
            }
//        root_dir = getCacheDir();

            //        root_dir = getCacheDir();
            val root = File(root_dir, File.separator + "MyDir" + File.separator)
            if (root.mkdirs()) {
                Log.d("SELECT_IMAGE", "root directory created")
            } else {
                Log.d("SELECT_IMAGE", "root directory failed to be created")
            }

            val tempFileName: String =  "img_" + System.currentTimeMillis() + ".jpg";
            tempImageFile = File(root, tempFileName)

            val outputFileUri = Uri.fromFile(tempImageFile)
            Log.d("SELECT_IMAGE", "outputFileUri: " + Utils.getPath(this, outputFileUri))

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(cameraIntent, cameraRequest)
        }

        viewModel.prediction_data.observe(this, Observer { data ->
            println("DATAAA->" + data.prediction)
            progress.dismiss()
            textView.text = data.prediction.predictionResult.toUpperCase()
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //var bitmap = data?.extras?.get("data") as Bitmap?
        //if (bitmap!= null)
        //wheatImage = convertBitmaptoFile(bitmap)
        //imageView.setImageBitmap(bitmap)
        val imgFile = File(tempImageFile.path)
        val bitmap = BitmapFactory.decodeFile(tempImageFile.path)
        imageView.setImageBitmap(bitmap)
        viewModel.getData(imgFile)

        progress.setTitle("Buğday Sınıflandırma Sistemi")
        progress.setMessage("Buğday Sınıflandırılıyor")
        progress.setCancelable(false) // disable dismiss by tapping outside of the dialog

        progress.show()
// To dismiss the
    }

    fun convertBitmaptoFile(bitmap: Bitmap) : File {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return file
    }
}