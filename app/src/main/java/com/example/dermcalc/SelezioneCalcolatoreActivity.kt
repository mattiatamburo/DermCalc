package com.example.dermcalc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SelezioneCalcolatoreActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_selezione_calcolatore)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idPaziente = intent.getIntExtra("idPaziente", -1)

        val btnPasi = findViewById<Button>(R.id.btn_PASI)
        val btnEasi = findViewById<Button>(R.id.btn_EASI)
        val btnBsa = findViewById<Button>(R.id.btn_BSA)
        val btnBmi = findViewById<Button>(R.id.btn_BMI)
        val btnAnnulla = findViewById<Button>(R.id.btn_Annulla)

        btnPasi.setOnClickListener {

            finish()
        }

        btnEasi.setOnClickListener {
            finish()
        }

        btnBsa.setOnClickListener {
            finish()
        }

        btnBmi.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            intent.putExtra("idPaziente", idPaziente)
            startActivity(intent)
            finish()
        }

        btnAnnulla.setOnClickListener {
            finish()
        }
    }
}