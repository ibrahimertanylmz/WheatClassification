package com.engresearch.wheatclassification

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var imageView = findViewById<ImageView>(R.id.imageView)
        var textview = findViewById<TextView>(R.id.textView)
        var button = findViewById<Button>(R.id.button)
        var editText = findViewById<EditText>(R.id.editTextTextPersonName)


    }
}