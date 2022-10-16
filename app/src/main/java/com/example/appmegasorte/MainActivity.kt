package com.example.appmegasorte

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val result = prefs.getString("result", null)


        val editText: EditText = findViewById(R.id.enter_numbers)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.generate_button)

        result?.let {
            txtResult.text = "Ultima aposta: $it"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()

            numberGeneratorResponse(text, txtResult)

        }

    }



    private fun numberGeneratorResponse(text: String, txtResult: TextView){
        if(text.isEmpty()){
            Toast.makeText(this, R.string.result_error, Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()
        if(qtd < 4 || qtd > 20){
            Toast.makeText(this, R.string.result_error, Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableListOf<Int>()
        val random = Random()

        while (true){
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if(numbers.size == qtd){
                break
            }

        }

        txtResult.text = numbers.sorted().joinToString(" | ")

        prefs.edit().apply(){
            putString("result", txtResult.text.toString())
            apply()
        }

    }

}