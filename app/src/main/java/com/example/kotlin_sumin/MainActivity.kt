package com.example.kotlin_sumin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var buttonSave: Button
    lateinit var textViewName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        buttonSave = findViewById(R.id.buttonSave)
        textViewName = findViewById(R.id.textViewName)


        // Старый вариант без лямбды
        buttonSave.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name = editTextName.text.toString().trim()
                textViewName.text = "Привет, $name!"
            }
        })
    }
}